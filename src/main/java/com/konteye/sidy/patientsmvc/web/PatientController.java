package com.konteye.sidy.patientsmvc.web;

import com.konteye.sidy.patientsmvc.entities.Patient;
import com.konteye.sidy.patientsmvc.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){

        Page<Patient> patientPage = patientRepository.findByNameContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listepatients",patientPage.getContent());
        model.addAttribute("pages",new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> patientsList(){
        List<Patient> patientEndpoint = patientRepository.findAll();
        return patientEndpoint ;
    }
    @GetMapping("/formPatients")
    public String formpatient (Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping("/save")
    public String save(Model model, @Valid Patient patient,BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/editPatient")
    public String editpatient (Model model, Long id, int page, String keyword){
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient==null) throw new RuntimeException("Patient Not found");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }

}
