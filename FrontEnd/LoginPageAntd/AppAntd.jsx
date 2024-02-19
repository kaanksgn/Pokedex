import {Button, ColorPicker, Form, Input, Typography} from "antd";
import "./AppAntd.css"
import AdminService from "./AdminService";
import { useEffect, useState } from "react";
import AdminPage from "./AdminPage";
import axios from "../../api/axios";
import FormItem from "antd/es/form/FormItem";
import UserPage from "./UserPage"
function AppAntd(){
    const [text, setText] = useState("")

    const [logedInUser, setLogedInUser] = useState(window.sessionStorage.getItem("user"))
    useEffect(()=>{
        setLogedInUser(window.sessionStorage.getItem("user"))
    },[])
    function login({username,password}){
       
        return axios.post("/login",{username,password},
            {headers:{'content-type': 'application/x-www-form-urlencoded'}}).then((response)=>{
                console.log(response.status)
                
                
                if(!response.data.payload.isAdmin){
                    console.log("User Logged In Successfully")
                    window.sessionStorage.setItem("user","user")
                    setLogedInUser("user")
                }else if(response.data.payload.isAdmin){
                    console.log("Admin Logged In Successfully")
                    window.sessionStorage.setItem("user","admin")
                    setLogedInUser("admin")
                }
            }) .catch(function (error){
                console.log(error.response.status)
                setText("Wrong Username of Password")
                setTimeout(()=>{setText("")},1000)
                
                
            })

    }
//background-image: linear-gradient(79deg,#7439db,#c66fbc 48% ,#f7944d);
    return( 
    logedInUser == null ? (<body id="logindiv" ><div className="appBg">
        <Form className="loginForm" autoComplete="off" onFinish={(values)=>{
            
            console.log({values}/*.values.username,{values}.values.password*/)
            /*AdminService.login({values}).then((response)=>{console.log(response)},);*/

            login(values)
           //console.log(AdminService.parameters({values}))
           /*console.log(AdminService.login({values}))*/
        }}>
            <Typography.Title className="title" style={{color:"white"}}>Pokedex Login</Typography.Title>
            <Form.Item hasFeedback rules={[
                    {required:true,
                        message:"Username is Required"},
                    {whitespace:true},
                    {min:3}]}
                 label={<label style={{ color: "white" }}>Username</label>} name={"username"}>

            <Input style={{width:250}} placeholder="Username"/>
            
            </Form.Item>
            
            <Form.Item rules={[{required:true,message:"Password is Required"}]} label={<label style={{ color: "white" }}>Password</label>} name={"password"}>
                <Input.Password style={{width:250}} placeholder="Password"/>
              
            </Form.Item >
             <FormItem>
                <h3 style={{transition:"0.7s!important",position:"absolute",width:250,right:-145, bottom:10,color:"#c90000"}}>{text}</h3>
                </FormItem>
            
            <Button type="primary" htmlType="submit"  className="button" >Login</Button>
        </Form>
    </div></body>) : logedInUser == "admin" ? <AdminPage setLogedInUser={setLogedInUser}/> : <UserPage setLogedInUser={setLogedInUser}/>
    
    )
}

export default AppAntd;