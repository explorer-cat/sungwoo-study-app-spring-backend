package testapp.demo.board.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;
import testapp.demo.board.entity.BoardBookmark;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBySubCategoryId(long subCategoryId);

    Board findBySubCategoryIdAndId(long subCategoryId, long postId);

    //target.title like %:keyword% OR target.content like %:keyword% AND
//    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds ORDER BY b.createTime :#{#sort}")
//    List<Board> findByKeywordAndSubCategoryIds(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds, @Param("sort") String sort);

//    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds ORDER BY :#{#sort.direction
//    }")
    //:#{#sort.direction}

    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.isRemove = false AND b.subCategory.id IN :subCategoryIds ORDER BY b.createTime ASC")
    List<Board> findByKeywordAndSubCategoryIdsCreateASC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds, Pageable pageable);
    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.isRemove = false  AND b.subCategory.id IN :subCategoryIds ORDER BY b.createTime DESC")
    List<Board> findByKeywordAndSubCategoryIdsCreateDESC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds, Pageable pageable);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.isRemove = false AND b.subCategory.id IN :subCategoryIds GROUP BY b ORDER BY COUNT(o) ASC")
    List<Board> findByKeywordAndSubCategoryIdsLikeASC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds, Pageable pageable);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.isRemove = false  AND b.subCategory.id IN :subCategoryIds GROUP BY b ORDER BY COUNT(o) DESC")
    List<Board> findByKeywordAndSubCategoryIdsLikeDESC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword%  OR b.content LIKE %:keyword%)AND b.isRemove = false  ORDER BY b.createTime ASC")
    List<Board> findAllByCreateSortASC(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword%  OR b.content LIKE %:keyword% ) AND b.isRemove = false ORDER BY b.createTime DESC")
    List<Board> findAllByCreateSortDESC(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% ) AND b.isRemove = false GROUP BY b ORDER BY COUNT(o) DESC")
    List<Board> findAllByLikeSortDESC(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% ) AND b.isRemove = false GROUP BY b ORDER BY COUNT(o) ASC")
    List<Board> findAllByLikeSortASC(@Param("keyword") String keyword, Pageable pageable);


    /**
     * SELECT *
     * FROM board b
     *          INNER JOIN board_bookmark bb ON b.board_id = bb.board_id
     * WHERE b.board_id IN (
     *     SELECT b.board_id
     *     FROM board b
     *              INNER JOIN board_bookmark bb ON b.board_id = bb.board_id
     *     WHERE b.user_email = 'rl0425@naver.com'
     * )
     *   AND bb.user_email = 'rl0425@naver.com'
     * @param email
     * @param pageable
     * @return
     */
//    @Query("SELECT b FROM Board b INNER JOIN b.boardBookmarks o WHERE b.member.email = :email ORDER BY b.createTime ASC")

    // SELECT * FROM board b INNER JOIN board_bookmark bb ON b.board_id = bb.board_id AND bb.user_email = 'rl0425@naver.com'
    @Query("SELECT b FROM Board b INNER JOIN b.boardBookmarks bb ON b.id = bb.board.id AND bb.member.email = :email ORDER BY b.createTime DESC")
    List<Board> findAllMyBookMarkPostASC(String email, Pageable pageable);
    @Query("SELECT b FROM Board b INNER JOIN b.boardBookmarks bb ON b.id = bb.board.id AND bb.member.email = :email ORDER BY b.createTime ASC")
    List<Board> findAllMyBookMarkPostDESC(String email, Pageable pageable);


    @Query("SELECT b FROM Board b WHERE b.member.email = :email ORDER BY b.createTime ASC")
    List<Board> findAllMyPostASC(String email, Pageable pageable);
    @Query("SELECT b FROM Board b WHERE b.member.email = :email ORDER BY b.createTime DESC")
    List<Board> findAllMyPostDESC(String email, Pageable pageable);

}
