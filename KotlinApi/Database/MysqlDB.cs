using System;
using KotlinApi.Model;
using Pomelo.Data.MySql;

namespace KotlinApi.Database
{
    public class MysqlDB
    {
      
       
        public bool checkUser(User user){
            //mysql bağlantı local host kendi adresinizide yazabilirsiniz
            using(MySqlConnection connMysql= new MySqlConnection(@"server=localhost;userid=root;password=;database=vbislife")){

                //command ile okuyup sorguluyoruz
                using(MySqlCommand cmd = connMysql.CreateCommand()){
                    cmd.CommandText = "SELECT * from userlist where name=@name and password=@pass";
                    cmd.Parameters.AddWithValue("@name",user.name);
                    cmd.Parameters.AddWithValue("@pass", user.password);
                   
					

					cmd.Connection = connMysql;

                    connMysql.Open();


                    using(MySqlDataReader reader = cmd.ExecuteReader()){

                        if (reader.Read())
                        {
                            //eğer bir data var ise true döndürüyoruz
                            return true;
                        }
                    }
                }
            }

            //bulunamazsa false döndürüyoruz
            return false;
        }
    }
}
