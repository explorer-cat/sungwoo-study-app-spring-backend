package testapp.demo.board.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;
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

    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds ORDER BY b.createTime ASC")
    List<Board> findByKeywordAndSubCategoryIdsCreateASC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds);
    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds ORDER BY b.createTime DESC")
    List<Board> findByKeywordAndSubCategoryIdsCreateDESC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds GROUP BY b ORDER BY COUNT(o) ASC")
    List<Board> findByKeywordAndSubCategoryIdsLikeASC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.subCategory.id IN :subCategoryIds GROUP BY b ORDER BY COUNT(o) DESC")
    List<Board> findByKeywordAndSubCategoryIdsLikeDESC(@Param("keyword") String keyword, @Param("subCategoryIds") List<Long> subCategoryIds);

    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword%  OR b.content LIKE %:keyword%) ORDER BY b.createTime ASC")
    List<Board> findAllByCreateSortASC(@Param("keyword") String keyword);
    @Query("SELECT b FROM Board b WHERE (b.title LIKE %:keyword%  OR b.content LIKE %:keyword% ) ORDER BY b.createTime DESC")
    List<Board> findAllByCreateSortDESC(@Param("keyword") String keyword);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% ) GROUP BY b ORDER BY COUNT(o) DESC")
    List<Board> findAllByLikeSortDESC(@Param("keyword") String keyword);
    @Query("SELECT b FROM Board b LEFT JOIN b.boardLike o WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% ) GROUP BY b ORDER BY COUNT(o) ASC")
    List<Board> findAllByLikeSortASC(@Param("keyword") String keyword);

    // 일반 SQL쿼리findAllByLikeSortASC


//    @Query(value = "select snack_id, name, price from snack", nativeQuery = true)
//    public List<Board> selectAllSQL();


}
