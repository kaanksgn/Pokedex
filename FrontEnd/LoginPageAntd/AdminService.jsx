import axios from 'axios'

const URL = "http://localhost:8080/login"
const params = new URLSearchParams();
class AdminService{

    parameters(values){
        params.append('username',{values}.values.username)
        params.append('password',{values}.values.password)

        return params
    }

    login({username,password}){
       
        return axios.post(URL,{username,password},
            {headers:{'content-type': 'application/x-www-form-urlencoded'},
            withCredentials:true}).then((response)=>{
                if(!response.data.payload.isAdmin){
                    console.log("User Logged In Successfully")
                    console.log(response.data.payload)
                }else if(response.data.payload.isAdmin){
                    console.log("Admin Logged In Successfully")
                }
            })

    }
}

export default new AdminService()