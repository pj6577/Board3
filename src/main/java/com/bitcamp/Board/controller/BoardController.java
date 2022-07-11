package com.bitcamp.Board.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitcamp.Board.model.BoardDTO;
import com.bitcamp.Board.model.UserDTO;
import com.bitcamp.Board.service.BoardService;
import com.bitcamp.Board.service.UserService;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserService userService;
    
    @RequestMapping("/showAll")
    public String showFirstPage() {
        return "redirect:/board/showAll/1";
    }
    
    @RequestMapping("/showAll/{pageNo}")
    public String showAll(Model model, HttpSession session, @PathVariable int pageNo) {
        UserDTO logIn = (UserDTO)session.getAttribute("logIn");
        if(logIn == null) {
            return "redirect:/";
        }
        
        List<BoardDTO> list = boardService.selectAll(pageNo);
        
        
        HashMap<Integer, String> nicknameMap = new HashMap<>();
        for(BoardDTO b : list) {
            nicknameMap.put(b.getWriterId(), 
                    userService.selectOne(b.getWriterId()).getNickname());
        }
        model.addAttribute("list", list);
        model.addAttribute("nicknameMap", nicknameMap);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("lastPageNo",boardService.selectLastPage());
        
        return "/board/showAll";
    }
    
    @RequestMapping("/selectOne/{id}")
    public String selectOne(@PathVariable int id, HttpSession session, Model model) {
        if(session.getAttribute("logIn") == null) {
            return "redirect:/";
        }
        
        BoardDTO b = boardService.selectOne(id);
        if(b == null) {
            return "redirect:/board/showAll";
        }
        
        model.addAttribute("b", b);
        model.addAttribute("nickname", userService.selectOne(b.getWriterId()).getNickname());
        UserDTO logIn = (UserDTO)session.getAttribute("logIn");
        model.addAttribute("logInId", logIn.getId());
        
        return "board/showOne";
    }
}











