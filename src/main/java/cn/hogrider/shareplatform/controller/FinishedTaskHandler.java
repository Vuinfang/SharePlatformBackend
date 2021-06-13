package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.FinishedApplication;
import cn.hogrider.shareplatform.entity.FinishedTask;
import cn.hogrider.shareplatform.entity.OngoingApplication;
import cn.hogrider.shareplatform.entity.OngoingTask;
import cn.hogrider.shareplatform.repository.FinishedApplicationRepository;
import cn.hogrider.shareplatform.repository.FinishedTaskRepository;
import cn.hogrider.shareplatform.repository.OngoingApplicationRepository;
import cn.hogrider.shareplatform.repository.OngoingTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finishedTask")
public class FinishedTaskHandler {
    @Autowired
    private OngoingTaskRepository ongoingTaskRepository;

    @Autowired
    private FinishedTaskRepository finishedTaskRepository;

    @Autowired
    private OngoingApplicationRepository ongoingApplicationRepository;

    @Autowired
    private FinishedApplicationRepository finishedApplicationRepository;

    //===========================
    //for finished task
    @GetMapping("/finished/{page}/{size}/{username}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return finishedTaskRepository.findByUser(username,pageable);
    }
    //find total task number
    @GetMapping("/finishedTotal/{username}")
    public Integer unstartedTotal(@PathVariable("username") String username){
        return finishedTaskRepository.unstartedTotal(username);
    }
    //get all task posted related to keyword
    @GetMapping("/finishedSearch/{page}/{size}/{username}/{keyword}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return finishedTaskRepository.findByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/finishedSearchTotal/{username}/{keyword}")
    public Integer unstartedTotal(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return finishedTaskRepository.unstartedTotal(username,keyword);
    }
    //===============================
    @GetMapping("/finishedApp/{keyword}")
    public List<FinishedApplication> ongoingApplications(@PathVariable("keyword") String keyword){
        return finishedApplicationRepository.findByApplicant(keyword);
    }

}
