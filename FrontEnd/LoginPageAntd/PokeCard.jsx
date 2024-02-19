import { Card, Divider, Form, Input, Modal, Space } from 'antd'
import React, { useState } from 'react'
import {
  DeleteOutlined,EditOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';

export default function PokeCard({name,type,desc,id}){
  const [pokename, setPokename] = useState(name)
  const [poketype, setPoketype] = useState(type)
  const [pokedesc, setPokedesc] = useState(desc)


  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleOk = () => {
    console.log(pokename)
    console.log(poketype)
    console.log(pokedesc)
    axios.post('/admindashboard/poke_update',{
      id: id,
      name: `${pokename}`,
      type : `${poketype}`,
      description : `${pokedesc}`,
   }).then(function(response){
      console.log(response.status)
   })


    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <><Card title={name} onHover style={{color:"white"}} headStyle={{color:"black"}} className='userCard'  actions={
        [
        
            <DeleteOutlined key="delete" onClick={()=>{deletePoke(id)}}/>,
            <EditOutlined key="edit"onClick={showModal}/>
        ]
        }>
            <p > Type : {type}</p>
            <p>Description : {desc}</p>
        </Card>
        <Modal okButtonProps={{type:"primary",htmlType:"submit"}}className="editUser"title="Edit Pokemon" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
       <Space direction='vertical'>
        <Divider/>
        <Form style={{display:"flex",flexDirection:"column",alignItems:"strecth",paddingLeft:"45px"}} onFinish={(values)=>{console.log(values)}}>

          <Form.Item label={<label className='editCardLabel' name={"pokename"}>Pokemon Name</label>}>
            <Input onChange ={(e)=>{setPokename(e.target.value)}} value={pokename} style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"poketype"}>Pokemon Type</label>}>
            <Input onChange ={(e)=>{setPoketype(e.target.value)}}value={poketype}style={{width:"content"}}/>
          </Form.Item>
          <Form.Item label={<label className='editCardLabel' name={"pokedesc"}>Pokemon Description</label>}>
            <Input onChange ={(e)=>{setPokedesc(e.target.value)}}value={pokedesc}style={{width:"max-content"}}/>
          </Form.Item>
         

        </Form>
        </Space>
    </Modal>
      </>  
  )
}

function deletePoke(id){
  debugger
    axios.post(`/admindashboard/poke_delete?pokeId=${id}`,{}).then(res=> console.log(res))
}