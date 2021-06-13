package cn.hogrider.shareplatform.controller;

import cn.hogrider.shareplatform.entity.*;
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
@RequestMapping("/ongoingTask")
public class OngoingTaskHandler {
    @Autowired
    private OngoingTaskRepository ongoingTaskRepository;

    @Autowired
    private FinishedTaskRepository finishedTaskRepository;

    @Autowired
    private OngoingApplicationRepository ongoingApplicationRepository;

    @Autowired
    private FinishedApplicationRepository finishedApplicationRepository;

    @GetMapping("/findAll")
    public List<OngoingTask> findAll(){
        return ongoingTaskRepository.findAll();
    }

    @PostMapping("/save")
    public String save(@RequestBody OngoingTask task){
        OngoingTask result = ongoingTaskRepository.save(task);
        if(result != null){
            return "success";
        } else {
            return "error";
        }
    }
    //============================
    //for personal ongoing task
    @GetMapping("/ongoing/{page}/{size}/{username}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username){
        Pageable pageable = PageRequest.of(page-1,size);
        return ongoingTaskRepository.findByUser(username,pageable);
    }
    //find total task number
    @GetMapping("/ongoingTotal/{username}")
    public Integer unstartedTotal(@PathVariable("username") String username){
        return ongoingTaskRepository.unstartedTotal(username);
    }
    //get all task posted related to keyword
    @GetMapping("/ongoingSearch/{page}/{size}/{username}/{keyword}")
    public List postedTask(@PathVariable("page") Integer page
            , @PathVariable("size") Integer size, @PathVariable("username") String username, @PathVariable("keyword") String keyword){
        Pageable pageable = PageRequest.of(page-1,size);
        keyword = "%"+keyword+"%";
        return ongoingTaskRepository.findByKeyword(username,keyword,pageable);
    }
    //find total task number by search keyword
    @GetMapping("/ongoingSearchTotal/{username}/{keyword}")
    public Integer unstartedTotal(@PathVariable("username") String username, @PathVariable("keyword") String keyword){
        keyword = "%"+keyword+"%";
        return ongoingTaskRepository.unstartedTotal(username,keyword);
    }
    //================================================
    @GetMapping("/ongoing/{keyword}")
    public List<OngoingApplication> ongoingApplications(@PathVariable("keyword") String keyword){
        return ongoingApplicationRepository.findByApplicant(keyword);
    }


    @GetMapping("/agree/{taskID}")
    public String findById(@PathVariable("taskID") Integer taskId)
    {
        Optional<OngoingTask> task = ongoingTaskRepository.findById(taskId);
        if(task.isPresent()){
            OngoingTask temTask = task.get();
            Optional<OngoingApplication> application = ongoingApplicationRepository.findByTaskID(temTask.getId());
            if(application.isPresent()){

                FinishedTask finishedTask = new FinishedTask();
                finishedTask.setDetails(temTask.getDetails());
                finishedTask.setId(temTask.getId());
                finishedTask.setPrice(temTask.getPrice());
                finishedTask.setTag(temTask.getTag());
                finishedTask.setTitle(temTask.getTitle());
                finishedTask.setUsername(temTask.getUsername());
                finishedTaskRepository.save(finishedTask);
                ongoingTaskRepository.delete(temTask);
                OngoingApplication temApplication = application.get();
                FinishedApplication finishedApplication = new FinishedApplication();
                finishedApplication.setApplicant(temApplication.getApplicant());
                finishedApplication.setDetails(temApplication.getDetails());
                finishedApplication.setId(temApplication.getId());
                finishedApplication.setTaskID(temApplication.getTaskID());
                finishedApplicationRepository.save(finishedApplication);
                ongoingApplicationRepository.delete(temApplication);
                return "success";
            }
            return "success";
        }
        return "error";

    }
    //==================================
}
