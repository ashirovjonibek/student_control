package uz.controlstudentserver.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.controlstudentserver.dto.SubjectDto;
import uz.controlstudentserver.entity.Groups;
import uz.controlstudentserver.entity.Subject;
import uz.controlstudentserver.entity.User;
import uz.controlstudentserver.payload.ApiResponse;
import uz.controlstudentserver.repository.GroupsRepository;
import uz.controlstudentserver.repository.SubjectRepository;
import uz.controlstudentserver.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupsRepository groupsRepository;

    public ApiResponse saveOrEdit(SubjectDto dto){
        List<Groups> groups=new ArrayList<>();
        Subject subject=new Subject();
        if (dto.getId()!=null){
            subject = subjectRepository.findById(dto.getId()).orElseThrow(()->new IllegalStateException("Subject not found"));
        }
        for (int i = 0; i < dto.getGroupIds().size(); i++) {
            groups.add(groupsRepository.findById(dto.getGroupIds().get(i)).orElseThrow(()->new IllegalStateException("group not found")));
        }
        subject.setGroups(groups);
        List<User> teachers = subject.getTeachers();
        teachers.add(userRepository.findById(dto.getTeacherIds()).orElseThrow(()->new IllegalStateException("user not found")));

        subject.setTeachers(teachers);
        Subject save = subjectRepository.save(subject);
        return new ApiResponse("Success",true,save);
    }

    public ApiResponse getOne(Integer id){
        try {
            Subject subject = subjectRepository.findById(id).orElseThrow(() -> new IllegalStateException("Subject not found"));
            return new ApiResponse("Success",true,subject);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResponse("Error",false);
        }
    }

    public ApiResponse findAllByTeacherId(UUID id){
        return new ApiResponse("ok", true, subjectRepository.findAllByTeachers(
                userRepository.findById(id).orElseThrow(()->new IllegalStateException("user not found for subject"))
        ));
    }

    public ApiResponse findAll(){
        return new ApiResponse("Success", true,subjectRepository.findAll());
    }

}
