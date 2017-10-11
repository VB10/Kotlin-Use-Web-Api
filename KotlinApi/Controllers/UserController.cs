using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using KotlinApi.Database;
using KotlinApi.Model;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace KotlinApi.Controllers
{
    [Route("api/[controller]")]
    public class UserController : Controller
    {
        MysqlDB db=new MysqlDB();
        // GET: api/values
        [HttpGet]
        public IActionResult Get(string name, string password)
        {

            var x = db.checkUser(new User { name = name, password = password });

			return Ok(new { result = x });


		}

       

 
    }
}
