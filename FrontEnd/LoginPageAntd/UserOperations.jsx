import React, { useEffect, useState } from 'react'
import ItemCard from './ItemCard'
import { Button, Divider, Input, List } from 'antd'
import axios from '../../api/axios'
import AddCardUser from './AddCardUser'

export default function UserOperations() {
    const [users, setUsers] = useState([])
    const [username,setUsername]= useState("")
    const [usersurname,setUsersurname]= useState("")
    const [userusername,setUserusername]= useState("")
    const click=()=>{
      
      var string = `/admindashboard/user_list?`
      string=username.length>0?string+`name=${username}&`:string;
      string= usersurname.length>0?string+`surname=${usersurname}&`:string;
      string= userusername.length>0?string+`username=${userusername}&`:string;
      console.log(string)
      axios.get(string,{}
      ).then(res => {
        setUsers(res.data.payload)
        console.log(res.data)
      })
  
      setUsername("")
      setUsersurname("")
      setUserusername("")
    }   



    const getUsers = () => {
        axios.get("/admindashboard/list_all",{}
        ).then(res => setUsers(res.data.payload))
        
    }
    useEffect(()=>{
        getUsers()
    },[])
    return (
    <>
    <Divider/>
    <div className='inputDiv'>
      <h3 style={{padding:"10px"}}>Name</h3>
    <input className='userOpInput' type='text' value={username} onChange={(e)=>{setUsername(e.target.value)}}></input>
    <h3 style={{padding:"10px"}}>Surname</h3>
    <input className='userOpInput' type='text' value={usersurname}  onChange={(e)=>{setUsersurname(e.target.value)}}></input>
    <h3 style={{padding:"10px"}}>Username</h3>
    <input className='userOpInput'  type='text' value={userusername}  onChange={(e)=>{setUserusername(e.target.value)}}></input>
    
    <Button className='userOpButton' onClick={click}>List Users</Button>

    </div>
    <Divider/>

    <List
    grid={{
      gutter: 13,
      column: 4,

    }}
    dataSource={users}
    renderItem={(item) => (
      <List.Item style={{marginLeft:"55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
         <ItemCard className="itemCard"isadmin={item.isAdmin} id={item.id} email={item.email} fullname={item.fullName} name={item.name} surname={item.surname} username={item.username} key={item.id} />
      </List.Item>
    )}
  ><List.Item style={{marginLeft:"55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
  <div><AddCardUser className="itemCard" /> </div>
</List.Item>
</List>


  </>
  )
}
