package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import testapp.demo.bookmark.dto.UserSubBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;

import java.util.List;

public interface BookMarkService {

    void addBookMark(String userEmail,long mainCategoryId) throws NotFound;

    void removeBookMark(String userEmail,long mainCategoryId) throws NotFound;

    List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound;


    void addSubCategoryBookMark(String userEmail,long subCatergoryId);

    void removeSubBookMark(String userEmail,long subCategoryId) throws NotFound;

    List<UserSubBookMarkDto>  getAllSubBookMark(String userEmail);

    List<UserSubBookMarkDto>  getSelctedSubBookMark(String userEmail);
}
