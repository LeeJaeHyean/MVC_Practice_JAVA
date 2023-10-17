package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.LogInDto;
import com.example.demo.model.BoardDao;


@Controller
@RequestMapping("/home")
public class BoardController {
   private BoardDao dao;
   
   @Autowired
   public BoardController(BoardDao dao) {
      this.dao = dao;
   }
   
   @RequestMapping("/login")
   public String login(HttpSession session) {
//      if(session != null && session.getAttribute("id") != null) {
//         session.invalidate();
//      }
	   
	   System.out.println("loginSucess2!!!!!!!!!! " + session.getAttribute("id"));
      return "login";
   }
   
   
   
   @RequestMapping("/logOut")
   public String logOut(HttpSession session) {
      if(session.getAttribute("id") != null) {
         session.invalidate();
      }
      return "redirect:/home/login";
   }
   
   @RequestMapping("/logAceppt")
   public String logAcceptPage(@ModelAttribute LogInDto loginDto, HttpSession session) {
      
      if(dao.selectLogin(loginDto.getId(), loginDto.getPw()) == null) {
         
         System.out.println(loginDto.getId() + " " + loginDto.getPw());

         return "redirect:/home/login";
      }
      
LogInDto login = dao.selectLogin(loginDto.getId(), loginDto.getPw());
      
      session.setAttribute("id", login.getId());
      session.setAttribute("pw", login.getPw());

      
      return "redirect:/home/login";
   }
   
   
   
   
   
   @RequestMapping("/list")
   public String main(Model model, HttpSession session) {
      if(session.getAttribute("id") == null) {

         return "redirect:/home/login";
      }
      
      
      ArrayList<BoardDto> dto_list = dao.selectBoard();
      model.addAttribute("dto_list",dto_list);

      return "list";
   }
   
   
   
   @RequestMapping("/board")
   public String memberBoard() {
      return "memberBoard";
   }

   
   @RequestMapping("/membership")
   public String membership() {
      return "membership";
   }
   
   @RequestMapping("/insert")
   public String insert(@ModelAttribute LogInDto loginDto, RedirectAttributes ra) {
      dao.insertMember(loginDto);
      ra.addFlashAttribute("add","회원가입이 완료되었당");
      return "redirect:/home/login";
   }

   
   @RequestMapping("/write")
   public String write() {
      return "write";
   }
   
   @RequestMapping("/writeOk")
   public String writeOk(@ModelAttribute BoardDto boardDto, HttpSession session) {
      boardDto.setId((String)session.getAttribute("id"));
      System.out.println(boardDto.toString());
      dao.writeOk(boardDto);
      return "redirect:/home/list";
   }
   
   @RequestMapping("/delete/{no}")
   public String delete(HttpSession session, @PathVariable int no) {
      dao.delete(no, (String)session.getAttribute("id"));
      System.out.println("잇힝~");
      return "redirect:/home/list";
   }


   
   @RequestMapping("/modify/{no}")
   public String modify(@PathVariable int no, Model model, HttpSession session) {
      String cont = dao.modify(no);
      session.setAttribute("no", no);
      model.addAttribute("cont", cont);
      return "modifyPage";
   }

   
   @RequestMapping("/modifyPage")
   public String modifyPage(@ModelAttribute BoardDto boardDto, HttpSession session ) {
      boardDto.setId((String)session.getAttribute("id"));
      boardDto.setNo((int)session.getAttribute("no"));
      System.out.println(boardDto);
      boardDto.setContents(boardDto.getContents());
      dao.modifyOK(boardDto);
      return "redirect:/home/list";
   }

   
  @RequestMapping("/view/{no}")
  public String viewOK(@PathVariable int no, Model model, HttpSession session) {
	  Map<String,Object> dataMap = dao.viewOK(no);
      session.setAttribute("no", no);
      
      System.out.println(dataMap);
      
      model.addAttribute("contents", dataMap.get("contents").toString());
      model.addAttribute("title", dataMap.get("title").toString());
      model.addAttribute("id", dataMap.get("id").toString());
      return "view";
   }

   
}