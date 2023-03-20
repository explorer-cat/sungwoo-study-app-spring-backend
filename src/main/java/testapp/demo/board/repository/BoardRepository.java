package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import testapp.demo.board.dto.BoardResponseDto;
import testapp.demo.board.entity.Board;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findBySubCategoryId(long subCategoryId);

    Board findBySubCategoryIdAndId(long subCategoryId, long postId);

//target.title like %:keyword% OR target.content like %:keyword% AND
    @Query(value = "select target from Board target where (target.title like %:keyword% OR target.content like %:keyword%) AND target.subCategory.id = :subCategoryId")
    List<Board> searchByPostSubCategory(@Param(value = "keyword") String keyword, @Param(value = "subCategoryId") Long subCategoryId);

//    @Query(value = "select target from Board target where target.title like %:keyword% OR target.content like %:keyword% AND target.subCategory")

    // 일반 JPQL쿼리, from뒤는 엔티티 명 (소문자로 할 시 에러)
    @Query(value = "select target from Board target where target.title like %:keyword% OR target.content like %:keyword%")
    List<Board> searchByContentOrTitle(@Param(value = "keyword") String keyword);


    // 일반 SQL쿼리
//    @Query(value = "select snack_id, name, price from snack", nativeQuery = true)
//    public List<Board> selectAllSQL();



}
