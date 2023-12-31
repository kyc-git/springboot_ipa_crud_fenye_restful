package com.how2java.springboot.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.how2java.springboot.dao.CategoryDAO;
import com.how2java.springboot.pojo.Category;

@Controller

// @RestController:@Controller+@ResponseBody；用于声明一个控制器类，并表示这是一个 RESTful 风格的控制器；
// @RestController 是 Spring Framework 提供的专门用于创建 RESTful Web 服务的注解
public class CategoryController {
	@Autowired CategoryDAO categoryDAO;
	
//    @RequestMapping("/listCategory")
	@GetMapping("/categories")//---------表明是restful风格
    public String listCategory(Model m,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
		//浏览器请求地址为../listCategory?start=1会让@RequestParam接收strat=1
		//@RequestParamSpring MVC 的数据绑定机制
    	start = start<0?0:start;
    	
    	Sort sort = new Sort(Sort.Direction.ASC, "id");
    	Pageable pageable = new PageRequest(start, size, sort);//定义分页规则：PageRequest对象包含了分页的相关信息，如页码、每页元素数量等。
    	Page<Category> page =categoryDAO.findAll(pageable);
    	//获取符合规则的  那一页   的数据+总页数+元素总数量

    	System.out.println(page.getNumber());
    	//获取当前页的页码，页码是从 0 开始的
    	System.out.println(page.getNumberOfElements());
    	//当前页实际元素数量；如果当前页没有元素，返回0
    	System.out.println(page.getSize());
    	//当前页实际元素数量；如果当前页没有元素，返回的是（页大小）
    	System.out.println(page.getTotalElements());//整个查询结果集中的元素总数量
    	System.out.println(page.getTotalPages());//获取总页数
    	
    	m.addAttribute("page", page);
    	
        return "listCategory";
    }



//	@RequestMapping("/addCategory")
    @PostMapping("/categories")
    public String addCategory(Category c) throws Exception {
    	categoryDAO.save(c);
    	return "redirect:/categories";
    }
//  @RequestMapping("/deleteCategory")
    @DeleteMapping("/categories/{id}")
    public String deleteCategory(Category c) throws Exception {
    	categoryDAO.delete(c);
    	return "redirect:/categories";
    }
//    @RequestMapping("/updateCategory")
	@PutMapping("/categories/{id}")
    public String updateCategory(Category c) throws Exception {
    	categoryDAO.save(c);
    	return "redirect:/categories";
    }

	@GetMapping("/categories/{id}")
	public String getCategory(@PathVariable("id") int id,Model m) throws Exception {
		Category c= categoryDAO.getOne(id);
		System.out.println("2。这行运行了");
		m.addAttribute("c", c);
		return "editCategory";
	}
}

