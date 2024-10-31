package vniot.star.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vniot.star.entity.Category;
import vniot.star.models.CategoryModel;
import vniot.star.services.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("")
	public String all(Model model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("list",list);
		
		return "admin/category/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {

		  CategoryModel category = new CategoryModel(); 
		  model.addAttribute("category",category); 
		  category.setIsEdit(false);
			return "admin/category/add";
	}
	
	@PostMapping("/save")
	public ModelAndView saveOrUpdate (ModelMap model,
			@Valid @ModelAttribute("category") CategoryModel cateModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/category/add");
		}
		Category entity = new Category();
		BeanUtils.copyProperties(cateModel,entity);
		categoryService.save(entity);
		String message="";
		if(cateModel.getIsEdit()==true) {
		message="Category is EDIT";
		}
		else {
			message="Category is SAVE";
		}
		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories",model);
		}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long categoryId) {
	    Optional<Category> optCategory = categoryService.findById(categoryId);
	    CategoryModel cateModel = new CategoryModel();
	    
	    // Check if category exists
	    if (optCategory.isPresent()) {
	        Category entity = optCategory.get();
	        // Copy entity data to cateModel
	        BeanUtils.copyProperties(entity, cateModel);
	        cateModel.setIsEdit(true);
	        // Add data to view
	        model.addAttribute("category", cateModel);
	        return new ModelAndView("admin/category/add", model);
	    }
	    
	    model.addAttribute("message", "Category is not exist!!!");
	    return new ModelAndView("forward:/admin/categories", model);
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long categoryId) {
			categoryService.deleteById(categoryId);
			model.addAttribute("message", "Category is deleted");
			return new ModelAndView("forward:/admin/categories", model);
		}
}
