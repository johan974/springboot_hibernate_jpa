// The root URL for the RESTful services
var rootURL = "http://localhost:8080/employees";


// TAB: LIST
// Initiate by getting all employees
findAll();

$('#btnRefresh').click(function() {
	findAll();
	return false;
});
$('#btnAdd').click( function() {
	// hide current page, show new page
    $('#tabnew').click();
    // newMovie();
    return false;
});

function findAll() {
    jQuery.support.cors = true;
    $.ajax({
        type: 'GET',
        accept: { json: "application/json, text/javascript" },
        url: rootURL,
        dataType: "json",
        success: renderList
    });
}

function renderList( data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    // remove the table entries beyond the header
    $('#employeelist tr').not(':first').remove();
    var html = '';
    $.each(list, function(index, employee) {
        html += '<tr><td>' + employee.id + '</td><td>' + employee.name + '</td><td>' + employee.salary + '</td><td>' + employee.designation +
            '</td><td><button onclick="editEmployee('+employee.id+');">Edit</button></td><td><button onclick="deleteEmployee('+employee.id+');">Delete</button></td></tr>';
    });
    $('#employeelist tr').first().after( html);
}

function deleteEmployee( empid) {
    // alert( 'Deleting emp id: ' + empid);
    jQuery.support.cors = true;
    var u = rootURL + "/" + empid;
    $.ajax({
        type: 'DELETE',
        url: u,
        success: function(data, textStatus, jqXHR){
            // alert('Employee deleted successfully');
            findAll();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('Employee delete error ' + empid);
        }
    });
}

function editEmployee( empid) {
	jQuery.support.cors = true;
	$.ajax({
		type: 'GET',
		url: rootURL + '/id/' + empid,
		accept: { json: "application/json, text/javascript" },
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			renderUpdateDetails( data);
			$('#tabupdate').click();
		}
	});
}

function renderUpdateDetails(employee) {
    console.log('> Set update details with results: id = ' + employee.id);
    $('#employee_update_id').val(employee.id);
    $('#employee_update_name').val(employee.name);
    $('#employee_update_salary').val(employee.salary);
    $('#employee_update_designation').val(employee.designation);
}

// TAB: CREATION

$('#btnAddForm').click(function() {
    addEmployee();
    return false;
});

function addEmployee() {
	jQuery.support.cors = true;
	var formdata = employeeCreateFormToJSON()
	var u = rootURL;
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: u,
		dataType: "json",
		data: formdata,
		success: function(data, textStatus, jqXHR){
            findAll();
            $('#tablist').click();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addMovie error: ' + textStatus);
		}
	});
}
// Helper function to serialize all the form fields into a JSON string
function employeeCreateFormToJSON() {
    var emplId = $('#employee_create_id').val();
    return JSON.stringify({
        "id": emplId == "" ? "0" : emplId,   // was 'null' but than there is a JSON error
        "name": $('#employee_create_name').val(),
        "salary": $('#employee_create_salary').val(),
        "designation": $('#employee_create_designation').val()
    });
}

// TAB: UPDATE

$('#btnUpdateForm').click(function() {
    updateEmployee();
    return false;
});

function updateEmployee() {
	jQuery.support.cors = true;
	var u = rootURL;
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: u,
		dataType: "json",
		data: employeeUpdateFormToJSON(),
		success: function (data, textStatus, jqXHR){
			// alert('Employee updated successfully');
            findAll();
            $('#tablist').click();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Employee update error: ' + textStatus);
		}
	});
}

function employeeUpdateFormToJSON() {
    var emplId = $('#employee_update_id').val();
    return JSON.stringify({
        "id": emplId == "" ? "0" : emplId,   // was 'null' but than there is a JSON error
        "name": $('#employee_update_name').val(),
        "salary": $('#employee_update_salary').val(),
        "designation": $('#employee_update_designation').val()
    });
}
