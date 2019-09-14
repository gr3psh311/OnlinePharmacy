var express = require("express");
var mysql = require("mysql");
var app = express();
//app.use(express.static('public'));
var BosyParser= require("body-parser");
app.use(BosyParser.json());


var connection = mysql.createConnection({
	    host     : 'localhost',
	    user     : 'root',
	    password : 'root',
	    database:'dbpharmacies'
});
connection.connect();


app.get('/getlistpharmacies',function(req,res){  
	    //var query = "select idCity,name,touristNumber,imageurl as listImage from city natural join cityimage where imagetype='list' ";
	      //var query = "select idPharmacy,ville,address,timing,phone_num,caisse_convention,facebook,localisation from pharmacies";
		var query = "select * from pharmacies";
	connection.query(query,function(error,results){
		        if (error) throw error;
		        res.send(JSON.stringify(results));

	    })
});

app.get('/getpharmacydetail/:id',function(req,res){  

	var query = "select ville,address,timing,phone_num,caisse_convention,facebook,localisation from pharmacies where idPharmacy=?";
	connection.query(query,[req.params.id],function(error,results){
		        if (error) throw error;
		        res.send(JSON.stringify(results[0]));

	    })
});


// post service
app.post('/adduser',function(req,res){ 
    var user= req.body
    var autoGeneratedPasswd = Math.random().toString(36).slice(-8);
    
    var insert = "INSERT INTO `users` (`nss`,`name`,`firstname`,`address`,`phone_num`,`password`) VALUES(?,?,?,?,?,?);";
    connection.query(insert,[user.nss, user.name, user.firstname, user.address, user.phone_num, autoGeneratedPasswd],function(error,results){
    if (error)
	    //res.send(req.body);
	    res.send(JSON.stringify("error"));
	    else {
		    res.send(JSON.stringify("success"));

		    const accountSid = 'AC33b2b2c03fa0311fb635695af13f01e3';
		    const authToken = '992293e28a4bb77a4da76e930d615874';
		    const client = require('twilio')(accountSid, authToken);

		    client.messages.create({
			    body: autoGeneratedPasswd,
			    from: '+13343986967',
			    //to: '+2130552556105'
			    to: '+213'+user.phone_num
		    }).then(message => console.log(message.sid));
	    
	    }
	    
})
});


app.get('/authuser/:phone/:password',function(req,res){
	
	var query = "SELECT * from users where phone_num=?;";
	connection.query(query,[req.params.phone],function(error,results){
		if(error || (results[0]=== undefined)) res.send(JSON.stringify(null));
		else {
			//console.log("res: ",results);
			console.log("res0: ",results[0]);
			if(results[0].password == req.params.password){
				res.send(JSON.stringify(results[0]));
			}
			else {
				res.send(JSON.stringify(null));
			}
		}
	})
});



var server = app.listen(8082,function(){
	    var host = server.address().address
	    var port = server.address().port
});

