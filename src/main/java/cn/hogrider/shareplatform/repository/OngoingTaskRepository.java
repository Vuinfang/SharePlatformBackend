package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.OngoingTask;
import cn.hogrider.shareplatform.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OngoingTaskRepository extends JpaRepository<OngoingTask, Integer> {
    final String countApplier = "select task.id as ID, task.title as Title, task.price as Price, application.applicant as Applicant, task.tag as Tag, task.details as Details from OngoingTask task left join OngoingApplication application on task.id = application.taskID where task.username = :username";
    final String searchPostedTask = "select task.id as ID, task.title as Title, task.price as Price, count(application.id) as TotalNumber, task.tag as Tag, task.details as Details from OngoingTask task left join OngoingApplication application on task.id = application.taskID where task.username = :username and task.title like :keyword group by task.id";

    //==============================
    @Query(countApplier)
    public List findByUser(String username, Pageable pageable);
    //find total task number
    @Query("select count(task.id) from OngoingTask task where task.username = :username")
    Integer unstartedTotal(String username);
    //for page personnal - ongoing task, return pageable data
    @Query(searchPostedTask)
    public List findByKeyword(String username, String keyword, Pageable pageable);
    //find total task number related to keyword
    @Query("select count(task.id) from OngoingTask task where task.username = :username and task.title like :keyword")
    Integer unstartedTotal(String username, String keyword);
    //=================================================

}
