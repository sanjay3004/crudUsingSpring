package com.example.getandpostmapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class StudentWeb {
    HashMap<Integer,Student> list=new HashMap();

    @GetMapping("/students")
    public Object getStudents(){
        if(list.size()==0){
            return "there is no data";
        }
        return list;
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity getById(@PathVariable int id){
        if(list.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(list.get(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody Student std){
        if(std.getId()!=0 && std.getName()!=null && std.getAge()!=0) {
            list.put(std.getId(), std);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("added successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("enter all details");
    }

    @PutMapping("/updateName")
    public ResponseEntity<String> updateName(@RequestBody Student std){
        if(list.containsKey(std.getId())){
            Student obj=list.get(std.getId());
            obj.setName(std.getName());
            addStudents(obj);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
    }

    @PutMapping("/updateAge")
    public ResponseEntity<String> updateAge(@RequestBody Student std){
        if(list.containsKey(std.getId())){
            Student obj=list.get(std.getId());
            obj.setAge(std.getAge());
            addStudents(obj);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        ArrayList<Integer> arrayList=new ArrayList<>(list.keySet());
        if(arrayList.contains(id)){
            list.remove(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("removed successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(){
        if(list.size()==0){
            return "this is already empty";
        }
        list.clear();
        return "deleted successfully";
    }

    @PostMapping("/studentArray")
    public ResponseEntity<String> addStudentArray(@RequestBody Student[] student){
        for(Student i:student){
            if(i.getId()!=0 && i.getName()!=null && i.getAge()!=0) {
                addStudents(i);
            }
            else{
                list.clear();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter all detail for id: "+ i.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("added successfully");
    }
}
