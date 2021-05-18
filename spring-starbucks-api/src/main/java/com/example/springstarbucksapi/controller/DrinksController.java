// tag::head[]
package com.example.springstarbucksapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import com.example.springstarbucksapi.model.Ingredient;
import com.example.springstarbucksapi.model.Ingredient.Type;
import com.example.springstarbucksapi.model.Drink;

@Slf4j
@Controller
@RequestMapping("/design")
public class DrinksController {

//end::head[]

@ModelAttribute
public void addIngredientsToModel(Model model) {
	List<Ingredient> ingredients = Arrays.asList(
    new Ingredient("AMER", "Americano", Type.DRINK),
	  new Ingredient("CAPP", "Cappuccino", Type.DRINK),
    new Ingredient("ESPR", "Espresso", Type.DRINK),
	  new Ingredient("LATT", "Latte", Type.DRINK),
    new Ingredient("MACH", "Machiatto", Type.DRINK),
    new Ingredient("MOCH", "Mocha", Type.DRINK),
	  new Ingredient("TALL", "Tall", Type.SIZE),
	  new Ingredient("GRAN", "Grande", Type.SIZE),
    new Ingredient("VENT", "Venti", Type.SIZE),
    new Ingredient("TREN", "Trenta", Type.SIZE),
	  new Ingredient("WHOL", "Whole", Type.MILK),
	  new Ingredient("NFAT", "Non Fat", Type.MILK),
    new Ingredient("SOY", "Soy", Type.MILK),
    new Ingredient("ALMO", "Almond", Type.MILK),
    new Ingredient("COCO", "Coconut", Type.MILK),
	  new Ingredient("WCREAM", "Whipped Cream", Type.TOPPINGS),
	  new Ingredient("CFOAM", "Cold Foam", Type.TOPPINGS),
    new Ingredient("CARMDRIZ", "Caramel Drizzle", Type.TOPPINGS),
    new Ingredient("MOCHDRIZ", "Mocha Drizzle", Type.TOPPINGS)
    );
	
	Type[] types = Ingredient.Type.values();
	for (Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
}
	
//tag::showDesignForm[]
  @GetMapping
  public String showDesignForm(Model model) {
    model.addAttribute("design", new Drink());
    return "design";
  }

//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesign[]
 */

//tag::processDesignValidated[]
  @PostMapping
  public String processDesign(@Valid @ModelAttribute("design") Drink design, Errors errors, Model model) {
    if (errors.hasErrors()) {
      return "design";
    }

    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);

    return "redirect:/orders/current";
  }

//end::processDesignValidated[]

//tag::filterByType[]
  private List<Ingredient> filterByType(
      List<Ingredient> ingredients, Type type) {
    return ingredients
              .stream()
              .filter(x -> x.getType().equals(type))
              .collect(Collectors.toList());
  }

//end::filterByType[]
// tag::foot[]
}
// end::foot[]
