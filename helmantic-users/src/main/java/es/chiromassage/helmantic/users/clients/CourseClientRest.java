package es.chiromassage.helmantic.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "helmantic-courses", url="host.docker.internal:8002")
public interface CourseClientRest {
    @DeleteMapping("/remove-course-user/{id}")
    void removeCourseUser(@PathVariable("id") Long id);
}
