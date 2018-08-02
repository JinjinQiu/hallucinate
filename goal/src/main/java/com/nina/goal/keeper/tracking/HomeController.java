package com.nina.goal.keeper.tracking;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.nina.goal.keeper.tracking.data.process.DBWriter;
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

	private DBWriter dbWriter;

	@Autowired
	public HomeController(DBWriter dbWriter) {
		this.dbWriter = dbWriter;
	}

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
			final String insert = "Insert into goal.basic_goal ( goal_id, goal_name, goal_parent, goal_children, goal_description, goal_status )\n"
					+ "values (?, ?, ?, ?, ?, ?);";
			final String existingCheck = "select * from basic_goal where goal_name like ? ";

			String dataToInsert = res.getId().toString() + "," + res.getName() + "," + "" + "," + "" + ","
					+ res.getDescription() + "," + res.getStatus().name();
			if (dbWriter.existingCheck(existingCheck, res.getName())) {
				dbWriter.update(insert, dataToInsert);
			}
		} catch (Exception e) {
			logger.error("Add a goal failed", e);
		}
		return res;
	}

	@ApiOperation(value = "Add a goal")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Goal> saveGoal(
			@RequestParam(name = "name", required = true, defaultValue = "Stranger") String name,
			@RequestParam(name = "description", required = false, defaultValue = "first goal") String description) {
		logger.info("Add a goal");
		Goal res = new Goal(name, description, goalStatus.set);
		try {
			final String insert = "Insert into goal.basic_goal ( goal_id, goal_name, goal_parent, goal_children, goal_description, goal_status )\n"
					+ "values (?, ?, ?, ?, ?, ?);";
			final String existingCheck = "select * from basic_goal where goal_name like ? ";

			String dataToInsert = res.getId().toString() + "," + res.getName() + "," + "" + "," + "" + ","
					+ res.getDescription() + "," + res.getStatus().name();
			if (!dbWriter.existingCheck(existingCheck, "%" + name + "%")) {
				dbWriter.update(insert, dataToInsert);
			}
			return new ResponseEntity<Goal>(res, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Add a goal failed", e);
			return new ResponseEntity<Goal>(res, HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Update a goal")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<String> updateGoal(@PathVariable String id,
			@RequestParam(name = "name", required = true, defaultValue = "Stranger") String name,
			@RequestParam(name = "description", required = true) String description,
			@RequestParam(name = "status", required = true) String status) {

		logger.info("Update a goal");
		try {
			final String update = "UPDATE goal.basic_goal SET goal_name = ?, goal_parent = ?, goal_children = ?, goal_description = ?, goal_status = ?  where goal_id = ?;";
			final String existingCheck = "select * from goal.basic_goal where goal_id = ? ";
			String dataToUpdate = name + "," + "" + "," + "" + "," + description + "," + status + "," + id;
			if (dbWriter.existingCheck(existingCheck, id)) {
				dbWriter.update(update, dataToUpdate);
			}
			return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("update a goal failed", e);
			return new ResponseEntity<String>("Product updated failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Delete a goal")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteGoal(@PathVariable String id) {
		logger.info("Delete a goal");
		try {
			final String delete = "delete from goal.basic_goal  where goal_id = ?";
			final String existingCheck = "select * from basic_goal where goal_id = ? ";

			if (dbWriter.existingCheck(existingCheck, id)) {
				dbWriter.update(delete, id);
			}
			return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("update a goal failed", e);
			return new ResponseEntity<String>("Product updated failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
