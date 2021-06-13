package cn.hogrider.shareplatform.repository;

import cn.hogrider.shareplatform.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    //the string is too long, but can not seperate to two part, or it will be error
    final String taskWithApplier = "select task.id as ID, task.title as Title, task.price as Price, application.id as ApplicationID, application.applicant as Applicant from Task task left join Application application on task.id = application.taskID where task.username = ?1";
    final String countApplier = "select task.id as ID, task.title as Title, task.price as Price, count(application.id) as TotalNumber, task.tag as Tag, task.details as Details from Task task left join Application application on task.id = application.taskID where task.username = ?1 group by task.id";
    final String searchPostedTask = "select task.id as ID, task.title as Title, task.price as Price, count(application.id) as TotalNumber, task.tag as Tag, task.details as Details from Task task left join Application application on task.id = application.taskID where task.username = :username and task.title like :keyword group by task.id";
    final String showApplicants = "select application.id, application.applicant, user.email, user.phone, user.occupation, application.details,application.taskID from Application application left join User user on user.username = application.applicant where application.taskID = ?1";
    public List<Task> findByTitleLike(String title);
    public List<Task> findByUsername(String username);
    //==============================for page personnal - unstarted task
    @Query(countApplier)
    public List findByUser(String username);
    //for page personnal - unstarted task, return pageable data
    @Query(countApplier)
    public List findByUser(String username, Pageable pageable);
    //find total task number
    @Query("select count(task.id) from Task task where task.username = :username")
    Integer unstartedTotal(String username);
    //for page personnal - unstarted task, return pageable data
    @Query(searchPostedTask)
    public List findByKeyword(String username, String keyword, Pageable pageable);
    //find total task number
    @Query("select count(task.id) from Task task where task.username = :username and task.title like :keyword")
    Integer unstartedTotal(String username, String keyword);
    //=============================
    @Query(showApplicants)
    public List showApplicants(Integer id);
    @Query("select count(task.id) from Task task")
    Integer showTotal();
    @Query("select count(task.id) from Task task where task.title like :keyword")
    Integer totalLikeKeyword(String keyword);

//    @Query("SELECT u FROM User u WHERE (:name is null or u.name = :name) and (:lastname is null"
//            null + " or u.lastname= :lastname)")
//    Page<User> search(@Param("name") String name, @Param("lastname") String lastname, Pageable pageable);
//
    @Query("SELECT u FROM Task u WHERE u.title like :keyword ")
    Page<Task> searchKeyword(String keyword, Pageable pageable);
    //============================
    //for personal applied untarted job
    //all job applied
    @Query("select task from Task task left join Application app on task.id = app.taskID where app.applicant = :applicant")
    List<Task> findAppByTaskID(String applicant, Pageable pageable);
    //
    @Query("select task from Application app left join Task task on task.id = app.taskID where app.applicant = :applicant and task.title like :keyword")
    public List<Task> findAppByKeyword(String applicant, String keyword, Pageable pageable);

}
