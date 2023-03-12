package testapp.demo.board.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.demo.auth.SecurityUtil;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.dto.CreatePostRequest;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardBookmark;
import testapp.demo.board.entity.BoardLike;
import testapp.demo.board.mapper.BoardBookmarkMapper;
import testapp.demo.board.mapper.BoardLikeMapper;
import testapp.demo.board.repository.BoardBookMarkRepository;
import testapp.demo.board.repository.BoardLikeRepository;
import testapp.demo.board.repository.BoardRepository;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;

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
    private final BoardBookMarkRepository boardBookMarkRepository;

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
                    .approval(true) //게시글 승인 여부.
                    .createTime(LocalDateTime.now()).build();
            Board save = boardRepository.save(board);
            return save;
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
        Board board = boardRepository.findBySubCategoryIdAndId(subCategoryId, postId);
        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> board_like_list = this.getUserLikesPostList();
        Set<Long> board_bookmark_list = this.getUserBookmarkPost();

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .mainCategory(getMainCategoryInfo(board))
                .subCategory(getSubCategoryInfo(board))
                .createTime(board.getCreateTime())
                .member_info(setUserInfo(board))
                .board_like(setUserLikePost(board, board_like_list))
                .bookmark_info(setUserBookmarkPost(board, board_bookmark_list))
                .build();
    }

    @Override
    public List<BoardResponseDto> getAllPost(long subCategoryId) {
        //해당 전체 게시글중에 사용자가 좋아요  혹은 즐겨찾기 하고 있는지 확인해야함
        List<Board> allCategory = boardRepository.findBySubCategoryId(subCategoryId);

        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> board_like_list = this.getUserLikesPostList();
        Set<Long> board_bookmark_list = this.getUserBookmarkPost();

        List<BoardResponseDto> dto = new ArrayList<>();

        for (Board v : allCategory) {
            //사용자 정보
            dto.add(BoardResponseDto.builder()
                    .id(v.getId())
                    .title(v.getTitle())
                    .content(v.getContent())
                    .createTime(v.getCreateTime())
                    .mainCategory(getMainCategoryInfo(v))
                    .subCategory(getSubCategoryInfo(v))
                    .member_info(setUserInfo(v))
                    .board_like(setUserLikePost(v, board_like_list))
                    .bookmark_info(setUserBookmarkPost(v, board_bookmark_list))
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
        if(!existLike.isPresent()) {
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

        if(existLike.isPresent()) {
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
        if(!existBookmark.isPresent()) {
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

        if(existBookmark.isPresent()) {
            boardBookMarkRepository.deleteByMemberAndBoard(member, board.get());
        }
    }


    public Set<Long> getUserLikesPostList() {
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());

        //해당 사용자가 좋아요 하고 있는 게시글 리스트
        List<BoardLikeMapper> boardLike_key_list = boardLikeRepository.findByMember(member);

        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> board_like_list = new HashSet<>();

        //사용자가 좋아요 해놓은 게시글ID 값들만 저장.
        for (BoardLikeMapper board_like_id : boardLike_key_list) {
            board_like_list.add(board_like_id.getBoardId());
        }
        return board_like_list;
    }

    public Map<String, Object> setUserLikePost(Board board, Set<Long> board_like_list) {
        Map<String, Object> board_like_info = new HashMap<>();
        board_like_info.put("total_like_count", board.getBoardLike().size());
        //사용자가 좋아요 한 게시글이 해당 게시글인지 확인해서 true false 반환.
        board_like_info.put("user_like_status",
                //토큰이 없는 비로그인 사용자인 경우 무조건 false
                SecurityUtil.getUserEmail().equals("anonymousUser") ? false : board_like_list.contains(board.getId()));
        return board_like_info;
    }

    public Map<String, Object> setUserInfo(Board board) {
        Map<String, Object> memberInfo = new HashMap<>();
        memberInfo.put("nickname", board.getMember().getNickname());
        memberInfo.put("profile_img", board.getMember().getThumbnailImage());
        return memberInfo;
    }

    public Set<Long> getUserBookmarkPost() {
        Member member = memberRepository.findByEmail(SecurityUtil.getUserEmail());

        //해당 사용자가 좋아요 하고 있는 게시글 리스트
        List<BoardBookmarkMapper> boardLike_key_list = boardBookMarkRepository.findByMember(member);

        //위에서 가져온값들을 Id만 따로 가져와서 Set에 저장
        Set<Long> board_bookmark_list = new HashSet<>();

        //사용자가 좋아요 해놓은 게시글ID 값들만 저장.
        for (BoardBookmarkMapper board_bookmark_id : boardLike_key_list) {
            board_bookmark_list.add(board_bookmark_id.getBoardId());
        }
        return board_bookmark_list;
    }

    public Map<String, Object> setUserBookmarkPost(Board board, Set<Long> board_bookmark_list) {
        Map<String, Object> board_bookmark_info = new HashMap<>();
        board_bookmark_info.put("total_bookmark_count", board.getBoardBookmarks().size());
        //사용자가 좋아요 한 게시글이 해당 게시글인지 확인해서 true false 반환.
        board_bookmark_info.put("user_bookmark_status",
                //토큰이 없는 비로그인 사용자인 경우 무조건 false
                SecurityUtil.getUserEmail().equals("anonymousUser") ? false : board_bookmark_list.contains(board.getId()));
        System.out.println("board_bookmark_info = " + board_bookmark_info);
        return board_bookmark_info;
    }

    public Map<String,Object> getMainCategoryInfo(Board board) {
        Map<String, Object> main_category_info = new HashMap<>();

        main_category_info.put("main_category_id", board.getMainCategory().getId());
        main_category_info.put("main_category_name", board.getMainCategory().getName());

        return main_category_info;
    }

    public Map<String,Object> getSubCategoryInfo(Board board) {
        Map<String, Object> sub_category_info = new HashMap<>();

        sub_category_info.put("sub_category_id", board.getSubCategory().getId());
        sub_category_info.put("sub_category_name", board.getSubCategory().getName());

        return sub_category_info;
    }
}

