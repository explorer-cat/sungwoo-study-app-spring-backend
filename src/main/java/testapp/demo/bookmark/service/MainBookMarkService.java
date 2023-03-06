package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.member.entity.Member;

import java.util.List;

public interface MainBookMarkService {

    void addBookMark(String userEmail,Long mainCategoryId) throws NotFound;

    List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound;
}
