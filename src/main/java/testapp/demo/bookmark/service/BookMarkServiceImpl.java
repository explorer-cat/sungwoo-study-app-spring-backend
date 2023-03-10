package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.dto.UserSubBookMarkDetailDto;
import testapp.demo.bookmark.dto.UserSubBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.entity.SubCategoryBookMark;
import testapp.demo.bookmark.repository.MainCategoryBookMarkRepository;
import testapp.demo.bookmark.repository.SubCategoryBookMarkRepository;
import testapp.demo.category.dto.subCategory.SubCategoryResponseDTO;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.entity.SubCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.category.repository.SubCategoryRepository;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookMarkServiceImpl implements BookMarkService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    @Autowired
    private MainCategoryBookMarkRepository mainCategoryBookMarkRepository;
    @Autowired
    private SubCategoryBookMarkRepository subCategoryBookMarkRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    @Transactional
    public void addBookMark(String userEmail, Long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();

        Optional<MainCategoryBookMark> has_bookmark = mainCategoryBookMarkRepository.findByMemberAndMainCategory(member, mainCategory);

        //데이터베이스에 없는 경우에만 새로 추가함.
        if (!has_bookmark.isPresent()) {
            MainCategoryBookMark mainCategoryBookMark = MainCategoryBookMark.createMainBookMark(member, mainCategory);
            mainCategoryBookMarkRepository.save(mainCategoryBookMark);
        }
    }

    @Override
    @Transactional
    public void removeBookMark(String userEmail, Long mainCategoryId) {
        Member member = memberRepository.findByEmail(userEmail);
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();

        Optional<MainCategoryBookMark> mainCategoryBookMark = mainCategoryBookMarkRepository.findByMemberAndMainCategory(member, mainCategory);

        if (mainCategoryBookMark.isPresent()) {
            mainCategoryBookMarkRepository.deleteByMemberAndMainCategory(member, mainCategory);
        } else {
            //해당 사용자가 북마크한 정보를 찾을 수 없음.
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound {
        //사용자가 이메일로 조회할 때
        Member member = memberRepository.findByEmail(userEmail);
        return member.getMainCategoryBookMark();
    }

    @Override
    public List<UserSubBookMarkDto> getAllSubBookMark(String userEmail) {
        //요청한 사용자의 정보를 가져옴.
        Member member = memberRepository.findByEmail(userEmail);
        List<MainCategory> all = mainCategoryRepository.findAll();
        for (MainCategory mainCategory : all) {
            System.out.println("mainCategory.getSubCategories(); = " + mainCategory.getSubCategories());
        }



        return null;
    }

    @Override
    public List<UserSubBookMarkDto> getSelctedSubBookMark(String userEmail) {
        //요청한 사용자의 정보를 가져옴.
        Member member = memberRepository.findByEmail(userEmail);

        List<SubCategoryBookMark> subCategoryBookMark = member.getSubCategoryBookMark();
        //사용자가 북마크 하고있는 서브카테고리의 부모 메인카테고리를 중복없이 반환하기 위한 Set
        Set<MainCategory> user_book_mark_list = new HashSet<>();

        //사용자가 북마크한 서브 카테고리 정보들 중에서 메인 카테고리 정보들만 중복 가져와서 중복제거함
        for (SubCategoryBookMark categoryBookMark : subCategoryBookMark) {
            user_book_mark_list.add(categoryBookMark.getMainCategory());
        }

        //user_book_mark_list  <- 현재 중복 제거된 메인카테고리 정보가 담겨있음.
        Set<MainCategory> userBookMarkList = user_book_mark_list;

        List<UserSubBookMarkDto> dto = new ArrayList<>();

        //DTO 반환을 위한 사용자가 북마크 하고있는 메인 카테고리 개수만큼  반복 시작
        for (MainCategory mainCategory : userBookMarkList) {

            //해당 메인 카테고리를 북마크 하고있는 And 사용자를 디비에서 찾음.
            List<SubCategoryBookMark> byMainCategoryAndMember = subCategoryBookMarkRepository.findByMainCategoryAndMember(mainCategory, member);

            List<UserSubBookMarkDetailDto> detail_sub_category = new ArrayList<>();

            //byMainCategoryAndMember 개수 = subCategorybook마크의 개수임. 해당 정보들은 dto로 한번더 만들어서 반환 dto 에 넣어줌.
            for (SubCategoryBookMark categoryBookMark : byMainCategoryAndMember) {
                detail_sub_category.add(UserSubBookMarkDetailDto.builder()
                        .sub_category_id(categoryBookMark.getSubCategory().getId())
                        .sub_category_name(categoryBookMark.getSubCategory().getName())
                        .selected(true)//전체 다 선택되어있는 상태
                        .createdDate(categoryBookMark.getCreateDate())
                        .build());
            }

            //반환 DTO 세팅
            UserSubBookMarkDto build = UserSubBookMarkDto.builder()
                    .main_category_id(mainCategory.getId())
                    .main_category_name(mainCategory.getName())
                    .bookmark_sub_categories(detail_sub_category)
                    .build();

            dto.add(build);
        }

        return dto;
//        return member.getSubCategoryBookMark();
    }
}


//        SubCategoryBookMark subCategoryBookMark = new SubCategoryBookMark();
//        List<SubCategoryBookMark> member1 = subCategoryBookMark.getMember(member);

//        //중복된 메인카테고리가 없기 위해 SET
//        Set temp = new HashSet();
//
//        UserSubBookMarkDto dto = null;
//사용자가 북마크하고있는 서브카테고리 정보들중에서 메인카테고리 정보들만 먼저 세팅함.
//        for (SubCategoryBookMark categoryBookMark : member1) {
//            dto = new UserSubBookMarkDto();
//            System.out.println("categoryBookMark = " + categoryBookMark.getMainCategory().getId());
//            dto.setMain_category_id(categoryBookMark.getMainCategory().getId());
////            dto.setSub_category_list(categoryBookMark.getSubCategory().getSubCategoryBookMarkList());
//            temp.add(dto);
//        }

//        System.out.println("dto = " + dto);
//
//            System.out.println("categoryBookMark = " + categoryBookMark.getMainCategory().getId());
//            System.out.println("categoryBookMark = " + categoryBookMark);categoryBookMark.getSubCategory().getSubCategoryBookMarkList();
//            a.put(categoryBookMark.getMainCategory().getId(), );
//            temp.add(a);

//            System.out.println("a = " + a);

//            System.out.println("categoryBookMark = " + categoryBookMark);

//        }


//        System.out.println("member = " + subCategoryBookMark.getMember(member));


//        return null;


//        List<UserSubBookMarkDto> dto = new ArrayList<>();
//        List<MainCategory> all = mainCategoryRepository.findAll();
//
//        UserSubBookMarkDto userSubBookMarkDto = null;
//
//        for (MainCategory mainCategory : all) {
//            List<Map<String,Object>> bookmark_list = new ArrayList<>();
//            Map<String,Object> bookmark_sub_category = new HashMap<>();
//
//            bookmark_list.add(bookmark_sub_category);
//            userSubBookMarkDto = new UserSubBookMarkDto();
//
//            userSubBookMarkDto.setMain_category_id(mainCategory.getId());
//            userSubBookMarkDto.setMain_category_name(mainCategory.getName());
//
//            List<SubCategoryBookMark> subCategoryBookMark1 = member.getSubCategoryBookMark();
//
//            for (SubCategoryBookMark categoryBookMark : subCategoryBookMark1) {
//                System.out.println("categoryBookMark = " + categoryBookMark.getMember().getSubCategoryBookMark());
//
//                List<SubCategoryBookMark> subCategoryBookMark = categoryBookMark.getMember().getSubCategoryBookMark();
//                for (SubCategoryBookMark bookMark : subCategoryBookMark) {
//                    bookMark
//                }
//                if(mainCategory.getId() == categoryBookMark.getSubCategory().getMainCategory().getId()) {
//                    bookmark_sub_category.put("sub_category_id",categoryBookMark.getSubCategory().getId());
//                    bookmark_sub_category.put("sub_category_name",categoryBookMark.getSubCategory().getName());
//                    bookmark_sub_category.put("book_mark_date",categoryBookMark.getSubCategory().getCreateDate());
//                }
//            }
//            bookmark_list.add(bookmark_sub_category);
//
//            userSubBookMarkDto.setSub_category_list(bookmark_list);
//            dto.add(userSubBookMarkDto);
//        }
//
//        return dto;
//        return dto;
//        System.out.println("userSubBookMarkDto = " + userSubBookMarkDto);

//        List<MainCategoryBookMark> mainCategoryBookMarks = member.getMainCategoryBookMark();

//        UserSubBookMarkDto userSubBookMarkDto = new UserSubBookMarkDto();
//        userSubBookMarkDto.setSub_category_list(subCategoryBookMark);

//        System.out.println("userSubBookMarkDto = " + subCategoryBookMark);
//        for (SubCategoryBookMark categoryBookMark : subCategoryBookMark) {
//
//            UserSubBookMarkDto userSubBookMarkDto = new UserSubBookMarkDto();
//
//            userSubBookMarkDto.setMain_category_id(categoryBookMark.getSubCategory().getMainCategory().getId());
//            userSubBookMarkDto.setMain_category_name(categoryBookMark.getSubCategory().getMainCategory().getName());
//
//            bookmark_sub_category.put("sub_category_id",categoryBookMark.getSubCategory());
//            bookmark_sub_category.put("sub_category_name",categoryBookMark.getSubCategory().getName());
//            bookmark_list.add(bookmark_sub_category);
//
//            userSubBookMarkDto.setSub_category_list(bookmark_list);
//            userSubBookMarkDto.setBookmark_date(categoryBookMark.getSubCategory().getCreateDate());
//            dto.add(userSubBookMarkDto);
//        }


//        System.out.println("bookmark_list = " + dto);

//        for (SubCategoryBookMark value : subCategoryBookMark) {
//            UserSubBookMarkDto userSubBookMarkDto = new UserSubBookMarkDto();
//
//            userSubBookMarkDto.setMain_category_id(value.getSubCategory().getMainCategory().getId());
//            userSubBookMarkDto.setMain_category_name(value.getSubCategory().getMainCategory().getName());
//
//        }

//            System.out.println("value = " + value);
//
//            UserSubBookMarkDto.builder()
//                    .main_category_id(value.getSubCategory().getMainCategory().getId())
//                    .main_category_name(value.getSubCategory().getMainCategory().getName())
//                    .sub_category_list(value.getSubCategory())
//                    .build();


//        return null;
//        System.out.println("member.getSubCategoryBookMark() = " + member.getSubCategoryBookMark());
//        return null;
//        return member.getSubCategoryBookMark();


