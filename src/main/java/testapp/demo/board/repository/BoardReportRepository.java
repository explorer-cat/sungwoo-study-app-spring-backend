package testapp.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testapp.demo.board.entity.BoardReport;

public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {


}
