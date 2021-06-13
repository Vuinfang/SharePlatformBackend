package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.repository.FinishedApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/finishedApplication")
public class FinishedApplicationHandler {
    @Autowired
    private FinishedApplicationRepository finishedApplicationRepository;

    //=====================
    //for personal applied
    //unstarted job
    //to get applied job
    @GetMapping("/finishedJob/{page}/{size}/{username}")
    public List finishedJob(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return finishedApplicationRepository.findAppsByTaskID(username,pageable);
    }

    //find total applied job number
    @GetMapping("/totalFinishedJob/{username}")
    public Integer totalFinishedJob(@PathVariable("username") String username){
        return finishedApplicationRepository.totalApplication(username);
    }
    //get all task posted related to keyword
    @GetMapping("/finishedJobSearch/{page}/{size}/{username}/{keyword}")
    public List finishedJobSearch(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return finishedApplicationRepository.findAppByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/totalFinishedJobSearch/{username}/{keyword}")
    public Integer totalFinishedJobSearch(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return finishedApplicationRepository.unstartedTotal(username,keyword);
    }
    //=================================

}
