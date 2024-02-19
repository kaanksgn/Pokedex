import { Card, Divider, Form, Input, Modal, Space } from 'antd'
import React, { useState ,useRef} from 'react'
import {
  DeleteOutlined,EditOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';
export default function ItemCard({name,surname,email,username,id,fullname,isadmin}) {
  const [cardname, setCardname] = useState(name)
  const [cardsurname,setCardsurname] = useState(surname)
  const [cardusername,setCardusername] = useState(username)
  const [cardemail,setCardemail] = useState(email)
  
 
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    axios.post('/admindashboard/user_update',{
      id: id,
      name: `${cardname}`,
      surname : `${cardsurname}`,
      username : `${cardusername}`,
      email : `${cardemail}`,
   }).then(function(response){
      console.log(response.status)
   })


    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
   <> <Card title={fullname} style={{color:"white"}}className='userCard' actions={
        [
            <DeleteOutlined key="delete" onClick={()=>{deleteItem(id)}}/>,
            <EditOutlined key="edit"onClick={showModal}/>
        ]
        } >
        <p>Name : {name}</p>
        <p>Surname : {surname}</p>
        <p>Username : {username}</p>
        <p>User Email : {email}</p>
        <h3>Role : {isadmin?"Admin":"User"}</h3>
    </Card>

    <Modal okButtonProps={{type:"primary",htmlType:"submit"}}className="editUser"title="Edit User" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
       <Space direction='vertical'>
        <Divider/>
        <Form style={{display:"flex",flexDirection:"column",alignItems:"strecth",paddingLeft:"45px"}} onFinish={(values)=>{console.log(values)}}>

          <Form.Item label={<label className='editCardLabel' name={"name"}>Name</label>}>
            <Input onChange ={(e)=>{setCardname(e.target.value)}} value={cardname} style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"surname"}>Surname</label>}>
            <Input onChange ={(e)=>{setCardsurname(e.target.value)}}value={cardsurname}style={{width:"content"}}/>
          </Form.Item>
          <Form.Item label={<label className='editCardLabel' name={"username"}>Username</label>}>
            <Input onChange ={(e)=>{setCardusername(e.target.value)}}value={cardusername}style={{width:"max-content"}}/>
          </Form.Item>
          <Form.Item label={<label className='editCardLabel' name={"email"}>E-mail</label>}>
            <Input onChange ={(e)=>{setCardemail(e.target.value)}} value={cardemail}style={{width:"content"}}/>
          </Form.Item>

        </Form>
        </Space>
    </Modal>
    </>
  )
}


function deleteItem(id){
  axios.post(`/admindashboard/user_delete?userId=${id}`,{}).then((res)=>console.log(res))
  
}