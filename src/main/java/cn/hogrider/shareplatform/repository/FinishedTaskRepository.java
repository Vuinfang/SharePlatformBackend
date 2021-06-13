package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.FinishedTask;
import cn.hogrider.shareplatform.entity.OngoingTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinishedTaskRepository extends JpaRepository<FinishedTask, Integer> {
    final String countApplier = "select task.id as ID, task.title as Title, task.price as Price, application.applicant as Applicant, task.tag as Tag, task.details as Details from FinishedTask task left join FinishedApplication application on task.id = application.taskID where task.username = :username";
    final String searchPostedTask = "select task.id as ID, task.title as Title, task.price as Price, count(application.id) as TotalNumber, task.tag as Tag, task.details as Details from FinishedTask task left join FinishedApplication application on task.id = application.taskID where task.username = :username and task.title like :keyword group by task.id";

    //==============================
    @Query(countApplier)
    public List findByUser(String username, Pageable pageable);
    //find total task number
    @Query("select count(task.id) from FinishedTask task where task.username = :username")
    Integer unstartedTotal(String username);
    //for page personnal - finished task, return pageable data
    @Query(searchPostedTask)
    public List findByKeyword(String username, String keyword, Pageable pageable);
    //find total task number of finished and search by keyword
    @Query("select count(task.id) from FinishedTask task where task.username = :username and task.title like :keyword")
    Integer unstartedTotal(String username, String keyword);
    //=================================================

}
