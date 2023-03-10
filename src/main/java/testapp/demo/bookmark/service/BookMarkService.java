package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import testapp.demo.bookmark.dto.UserSubBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;

import java.util.List;

public interface BookMarkService {

    void addBookMark(String userEmail,Long mainCategoryId) throws NotFound;

    void removeBookMark(String userEmail,Long mainCategoryId) throws NotFound;

    List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound;

    List<UserSubBookMarkDto>  getAllSubBookMark(String userEmail);
    List<UserSubBookMarkDto>  getSelctedSubBookMark(String userEmail);
}
