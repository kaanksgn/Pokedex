import { Card, Divider, Form, Input, Select, Space ,Modal} from 'antd'
import React, { useState } from 'react'
import {
 PlusCircleOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';


export default function AddCardUser(){
  const [cardname, setCardname] = useState('')
  const [cardsurname,setCardsurname] = useState('')
  const [cardusername,setCardusername] = useState('')
  const [cardemail,setCardemail] = useState('')
  const [cardpassw,setCardpassw] = useState('')
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isadmin, setIsadmin] = useState();
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    console.log(`name: ${cardname},
    surname : ${cardsurname},
    username : ${cardusername},
    email : ${cardemail},
    password : ${cardpassw},
    isAdmin : ${isadmin}`)
    var link = "/admindashboard/"

    link= isadmin? link+"admin_add": link+"user_add";

       axios.post(link,{
          name: `${cardname}`,
          surname : `${cardsurname}`,
          username : `${cardusername}`,
          email : `${cardemail}`,
          password : `${cardpassw}`,
       }).then(function(response){
          console.log(response.data.code)
       })

    console.log(link)
    
    handleCancel()
  };
  const handleCancel = () => {
    setIsModalOpen(false);
    Modal.destroyAll();
    setCardname("")
    setCardsurname("")
    setCardusername("")
    setCardemail("")
    setCardpassw("")
    setIsadmin()
  
  };
  const handleChange = (val) =>{

    setIsadmin(val)
  }

  return (<>
    <Card  onClick={showModal}className='userCard'>
            <PlusCircleOutlined style={{fontSize:"70px",color:"white"}}/>
        </Card>

    <Modal destroyOnClose={true} okButtonProps={{type:"primary",htmlType:"submit"}}className="editUser"title="Add New User" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
      <Space direction='vertical'>
        <Divider/>
        <Form style={{display:"flex",flexDirection:"column",alignItems:"strecth",paddingLeft:"45px"}}>

          <Form.Item label={<label className='editCardLabel' name={"name"}>Name</label>}>
            <Input onChange ={(e)=>{setCardname(e.target.value)}} value={cardname}style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"surname"}>Surname</label>}>
              <Input onChange ={(e)=>{setCardsurname(e.target.value)}} value={cardsurname} style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"username"}>Username</label>}>
            <Input onChange ={(e)=>{setCardusername(e.target.value)}} value={cardusername} style={{width:"max-content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"email"}>E-mail</label>}>
            <Input onChange ={(e)=>{setCardemail(e.target.value)}} value={cardemail} style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"passw"}>Password</label>}>
            <Input onChange ={(e)=>{setCardpassw(e.target.value)}} value={cardpassw} style={{width:"content"}}/>
          </Form.Item>
          <Form.Item label={<label className='editCardLabel' name={"passw"}>User Role</label>}>
          <Select value={isadmin} onChange={handleChange}placeholder="Select Role" options={[{value:false,label:"User"},{value:true,label:"Admin"}]}/>
          </Form.Item>
         

 </Form>
 </Space>
</Modal></>
  )
}

