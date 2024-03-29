package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.dto.reportPostRequest;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardBookmark;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.board.entity.BoardReport;
import testapp.demo.board.mapper.BoardBookmarkMapper;
import testapp.demo.board.mapper.BoardLikeMapper;
import testapp.demo.board.repository.BoardBookMarkRepository;
import testapp.demo.board.repository.BoardLikeRepository;
import testapp.demo.board.repository.BoardReportRepository;
import testapp.demo.board.repository.BoardRepository;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final SubCategoryRepository subCategoryRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final BoardLikeRepository boardLikeRepository;
    @Autowired
    private final BoardBookMarkRepository boardBookMarkRepository;
    @Autowired
    private final BoardReportRepository boardReportRepository;

    /**
     * @param data
     * @return
     * @title: 게시글 작성
     */
    @Override
    public Board createPost(long subCategoryId, CreatePostRequest data) throws Exception {
        try {
            SubCategory subCategory = subCategoryRepository.findById(subCategoryId).get();
            Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());

            Board board = Board.builder()
                    .title(data.getTitle())
                    .mainCategory(subCategory.getMainCategory())
                    .subCategory(subCategory)
                    .member(member)
                    .content(data.getContent())
                    .approval(0) //게시글 승인 여부.
                    .createTime(LocalDateTime.now()).build();

            Board save = boardRepository.save(board);
            return save;
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @Override
    public void modifyPost(long postId, CreatePostRequest data) throws Exception {
        try {
            Board board = boardRepository.findById(postId).get();
            SubCategory subCategory = subCategoryRepository.findById(data.getSubcategory_id()).get();

            board.setTitle(data.getTitle());
            board.setContent(data.getContent());
            board.setSubCategory(subCategory);

            boardRepository.save(board);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * @return List<Board>
     * @Title : 카테고리 단건 게시글 조회.
     */
    @Override
    public BoardResponseDto getPost(long subCategoryId, long postId) {
        BoardLike boardLike = new BoardLike();
        BoardBookmark boardBookmark = new BoardBookmark();

        String userEmail = SecurityUtil.getUserEmail();

        Board board = boardRepository.findBySubCategoryIdAndId(subCategoryId, postId);
        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());

        //해당 사용자가 좋아요 하고 있는 게시글 리스트
        List<BoardBookmarkMapper> boardBookmarkMappers = boardBookMarkRepository.findByMember(member);
        //해당 게시글을 좋아요 하고 있는 사용자 리스트 뽑아오기.
        List<BoardLikeMapper> boardLikeMappers = boardLikeRepository.findByMember(member);

        Set<Long> board_like_list = boardLike.getUserLikesPostList(member, boardLikeMappers);
        Set<Long> board_bookmark_list = boardBookmark.getUserBookmarkPost(member, boardBookmarkMappers);

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .approval(board.getApproval())
                .isAuthor(userEmail.equals(board.getMember().getEmail()))
                .mainCategory(board.getMainCategoryInfo(board))
                .subCategory(board.getSubCategoryInfo(board))
                .createTime(board.getCreateTime())
                .member_info(board.setUserInfo(board))
                .board_like(boardLike.setUserLikePost(board, board_like_list))
                .bookmark_info(boardBookmark.setUserBookmarkPost(board, board_bookmark_list))
                .build();
    }

    @Override
    public List<BoardResponseDto> getMyPost(String sortType, int paging_num, int paging_count) throws Exception {
        List<BoardResponseDto> result = new ArrayList<>();

        try {
            Member findMember = memberRepository.findByEmail(SecurityUtil.getUserEmail());

            Pageable pageable = PageRequest.of(paging_num, paging_count);

            sortType = sortType.toLowerCase();

            List<Board> boards = sortType.equals("asc") ?
                    boardRepository.findAllMyPostASC(findMember.getEmail(), pageable) :
                    boardRepository.findAllMyPostDESC(findMember.getEmail(), pageable);

            for (Board v : boards) {
                result.add(BoardResponseDto.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .content(v.getContent())
                        .createTime(v.getCreateTime())
                        .approval(v.getApproval())
                        .isAuthor(true)
                        .mainCategory(v.getMainCategoryInfo(v))
                        .subCategory(v.getSubCategoryInfo(v))
                        .member_info(v.setUserInfo(v))
                        .build());
            }
            return result;
        } catch (NullPointerException ex) {
            return result;
        } catch (Exception ex) {
            System.err.println("getMyPost() - " + ex);
            throw new Exception("error");
        }
    }

    @Override
    @Transactional
    public void removePost(long postId) {
        Board targetBoard = boardRepository.findById(postId).get();
        if(targetBoard.getMember().getEmail().equals(SecurityUtil.getUserEmail())) {
            targetBoard.setRemove(true);
            boardRepository.save(targetBoard);
        } else {
            throw new IllegalArgumentException("AUTH");
        }
    }

    @Override
    public List<BoardResponseDto> getMyBookMarkPost(String sortType, int paging_num, int paging_count) throws Exception {
        List<BoardResponseDto> result = new ArrayList<>();
        try {
            Member findMember = memberRepository.findByEmail(SecurityUtil.getUserEmail());

            Pageable pageable = PageRequest.of(paging_num, paging_count);

            sortType = sortType.toLowerCase();

            List<Board> boards = sortType.equals("asc") ?
                    boardRepository.findAllMyBookMarkPostASC(findMember.getEmail(), pageable) :
                    boardRepository.findAllMyBookMarkPostDESC(findMember.getEmail(), pageable);

            for (Board v : boards) {
                result.add(BoardResponseDto.builder()
                        .id(v.getId())
                        .title(v.getTitle())
                        .content(v.getContent())
                        .createTime(v.getCreateTime())
                        .approval(v.getApproval())
                        .isAuthor(true)
                        .mainCategory(v.getMainCategoryInfo(v))
                        .subCategory(v.getSubCategoryInfo(v))
                        .member_info(v.setUserInfo(v))
                        .build());
            }
            return result;
        } catch (NullPointerException ex) {
            return result;
        } catch (Exception ex) {
            System.err.println("getMyBookMarkPost() - " + ex);
            throw new Exception("error");
        }
    }

    @Override
    public List<BoardResponseDto> getAllPost(List<Long> subCategories, String keyword, String sortTarget, String sortType, int paging_num, int paging_count) {
        BoardLike boardLike = new BoardLike();
        BoardBookmark boardBookmark = new BoardBookmark();
        //해당 전체 게시글중에 사용자가 좋아요  혹은 즐겨찾기 하고 있는지 확인해야함
        List<Board> allCategory = new ArrayList<>();
        String userEmail = SecurityUtil.getUserEmail();
        Pageable pageable = PageRequest.of(paging_num, paging_count);

        sortType = sortType.toLowerCase();
        sortTarget = sortTarget.toLowerCase();

        if (subCategories.size() > 0) {
            switch (sortTarget) {
                case "createtime":
                    allCategory = sortType.equals("asc") ?
                            boardRepository.findByKeywordAndSubCategoryIdsCreateASC(keyword, subCategories, pageable) :
                            boardRepository.findByKeywordAndSubCategoryIdsCreateDESC(keyword, subCategories, pageable);
                    break;
                case "like":
                    allCategory = sortType.equals("asc") ?
                            boardRepository.findByKeywordAndSubCategoryIdsLikeASC(keyword, subCategories, pageable) :
                            boardRepository.findByKeywordAndSubCategoryIdsLikeDESC(keyword, subCategories, pageable);
                    break;
            }
        } else {
            switch (sortTarget) {
                case "createtime":
                    allCategory = sortType.equals("asc") ?
                            boardRepository.findAllByCreateSortASC(keyword, pageable) :
                            boardRepository.findAllByCreateSortDESC(keyword, pageable);
                    break;
                case "like":
                    allCategory = sortType.equals("asc") ?
                            boardRepository.findAllByLikeSortASC(keyword, pageable) :
                            boardRepository.findAllByLikeSortDESC(keyword, pageable);
                    break;
            }
        }


        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());
        //해당 사용자가 좋아요 하고 있는 게시글 리스트
        List<BoardBookmarkMapper> boardBookmarkMappers = boardBookMarkRepository.findByMember(member);
        //해당 게시글을 좋아요 하고 있는 사용자 리스트 뽑아오기.
        List<BoardLikeMapper> boardLikeMappers = boardLikeRepository.findByMember(member);

        Set<Long> board_like_list = boardLike.getUserLikesPostList(member, boardLikeMappers);
        Set<Long> board_bookmark_list = boardBookmark.getUserBookmarkPost(member, boardBookmarkMappers);


        List<BoardResponseDto> dto = new ArrayList<>();

        for (Board v : allCategory) {
            dto.add(BoardResponseDto.builder()
                    .id(v.getId())
                    .title(v.getTitle())
                    .content(v.getContent())
                    .createTime(v.getCreateTime())
                    .approval(v.getApproval())
                    .isAuthor(userEmail.equals(v.getMember().getEmail()) ? true : false)
                    .mainCategory(v.getMainCategoryInfo(v))
                    .subCategory(v.getSubCategoryInfo(v))
                    .member_info(v.setUserInfo(v))
                    .board_like(boardLike.setUserLikePost(v, board_like_list))
                    .bookmark_info(boardBookmark.setUserBookmarkPost(v, board_bookmark_list))
                    .build());
        }
        //각 페이지 별로 페이징 처리도 해야함.
        return dto;
    }

    @Override
    @Transactional
    public void addPostLike(long postId) {
        //이미 해당 게시글을 좋아요 하고있는지 검증이 필요.
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());
        Optional<Board> board = boardRepository.findById(postId);

        Optional<BoardLike> existLike = boardLikeRepository.findByBoardAndMember(board.get(), member);

        //사용자가 해당 게시글을 좋아요 하고 있지 않는 경우에만 새로 추가.
        if (!existLike.isPresent()) {
            BoardLike boardLike = BoardLike.addBoardLike(board.get(), member);
            boardLikeRepository.save(boardLike);
        }
    }

    @Override
    @Transactional
    public void cancelPostLike(long postId) {
        //해당 게시글을 실제로 좋아요 하고있는지 검증 필요
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());
        Optional<Board> board = boardRepository.findById(postId);

        Optional<BoardLike> existLike = boardLikeRepository.findByBoardAndMember(board.get(), member);

        if (existLike.isPresent()) {
            boardLikeRepository.deleteByMemberAndBoard(member, board.get());
        }
    }

    @Override
    @Transactional
    public void addPostBookmark(long postId) {
        //이미 해당 게시글을 좋아요 하고있는지 검증이 필요.
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());
        Optional<Board> board = boardRepository.findById(postId);

        Optional<BoardBookmark> existBookmark = boardBookMarkRepository.findByBoardAndMember(board.get(), member);

        //사용자가 해당 게시글을 북마크 하고 있지 않는 경우에만 새로 디비에 추가
        if (!existBookmark.isPresent()) {
            BoardBookmark boardBookmark = BoardBookmark.addBoardBookmark(board.get(), member);
            boardBookMarkRepository.save(boardBookmark);
        }
    }

    @Override
    @Transactional
    public void cancelPostBookmark(long postId) {
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());
        Optional<Board> board = boardRepository.findById(postId);

        Optional<BoardBookmark> existBookmark = boardBookMarkRepository.findByBoardAndMember(board.get(), member);

        if (existBookmark.isPresent()) {
            boardBookMarkRepository.deleteByMemberAndBoard(member, board.get());
        }
    }

    @Override
    public void reportPost(reportPostRequest request) {
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());

        boardReportRepository.save(BoardReport.builder()
                .board(boardRepository.findById(request.getBoard_id()).get())
                .member(member)
                .reason(request.getReport_reason())
                .createTime(LocalDateTime.now())
                .build());
    }
}

