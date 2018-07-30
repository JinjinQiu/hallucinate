package com.nina.goal.keeper.tracking;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nina.goal.keeper.tracking.data.process.FileReaderWriter;
import com.nina.goal.keeper.tracking.data.type.Goal;
import com.nina.goal.keeper.tracking.data.type.Goal.goalStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/goal")
@Api(value = "Record all the goals")
public class HomeController {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HomeController.class);
	// private FileReaderWriter fileOp;

	@GetMapping("/list")
	@ResponseBody
	public Goal sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name,
			@RequestParam(name = "description", required = false, defaultValue = "first goal") String description) {
		logger.info("Add a goal");
		Goal res = new Goal(name, description, goalStatus.set);
		try {
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(res);
			FileReaderWriter fileOp = new FileReaderWriter("", res.getId().toString() + ".txt", data);
			fileOp.writeFile();
		} catch (Exception e) {
			logger.error("Add a goal failed", e);
		}
		return res;
	}

	@ApiOperation(value = "Add a goal")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Goal> saveProduct(@RequestBody Goal product) {
		logger.info("Add a goal");
		Goal rs = new Goal(product.getName(), product.getDescription(), product.getStatus());
		return new ResponseEntity<Goal>(rs, HttpStatus.OK);
	}

	@ApiOperation(value = "Update a goal")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Goal product) {
		logger.info("Update a goal");
		return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a goal")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> delete(@PathVariable String id) {
		logger.info("Delete a goal");
		return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);

	}

}
