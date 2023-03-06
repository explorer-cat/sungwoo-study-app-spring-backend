package testapp.demo.bookmark.service;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testapp.demo.bookmark.dto.UserMainBookMarkDto;
import testapp.demo.bookmark.entity.MainCategoryBookMark;
import testapp.demo.bookmark.repository.MainCategoryBookMarkRepository;
import testapp.demo.category.entity.MainCategory;
import testapp.demo.category.repository.MainCategoryRepository;
import testapp.demo.member.dto.UserInfoResponseDto;
import testapp.demo.member.entity.Member;
import testapp.demo.member.repository.MemberRepository;
import testapp.demo.member.service.MemberServiceImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainBookMarkServiceImpl implements MainBookMarkService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MainCategoryRepository mainCategoryRepository;
    @Autowired
    private MainCategoryBookMarkRepository mainCategoryBookMarkRepository;



    @Override
    @Transactional
    public void addBookMark(String userEmail, Long mainCategoryId) {
        try {
            Member member = memberRepository.findByEmail(userEmail);

            MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId).get();
            MainCategoryBookMark mainCategoryBookMark = MainCategoryBookMark.createMainBookMark(member, mainCategory);

            mainCategoryBookMarkRepository.save(mainCategoryBookMark);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Override
    public List<MainCategoryBookMark> getMainBookMark(String userEmail) throws NotFound {
        //사용자가 이메일로 조회할 때
        Member member = memberRepository.findByEmail(userEmail);
        return member.getMainCategoryBookMark();
    }
}
