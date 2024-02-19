import { Card, Divider, Form, Input, Select, Space ,Modal} from 'antd'
import React, { useState } from 'react'
import {
 PlusCircleOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';

export default function AddCardPoke(){
  const [cardname, setCardname] = useState("")
  const [cardtype, setCardtype] = useState("")
  const [carddesc, setCarddesc] = useState("")
  
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    console.log(
      `name: ${cardname},
      type : ${cardtype},
      desc : ${carddesc}`)
    var link = "/admindashboard/poke_add"

    

       axios.post(link,{
          name: `${cardname}`,
          type : `${cardtype}`,
          description : `${carddesc}`
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
    setCardtype("")
    setCarddesc("")
  };

  return (
    <>
    <Card onClick={showModal} className='userCard'>
      <PlusCircleOutlined style={{fontSize:"70px",color:"white"}}/>
    </Card>

    <Modal okButtonProps={{type:"primary",htmlType:"submit"}}className="editUser"title="Create New Pokemon" open={isModalOpen} onOk={handleOk} onCancel={handleCancel}>
       <Space direction='vertical'>
        <Divider/>
        <Form style={{display:"flex",flexDirection:"column",alignItems:"strecth",paddingLeft:"45px"}} onFinish={(values)=>{console.log(values)}}>

          <Form.Item label={<label className='editCardLabel' name={"pokename"}>Pokemon Name</label>}>
            <Input onChange ={(e)=>{setCardname(e.target.value)}} value={cardname} style={{width:"content"}}/>
          </Form.Item>

          <Form.Item label={<label className='editCardLabel' name={"poketype"}>Pokemon Type</label>}>
            <Input onChange ={(e)=>{setCardtype(e.target.value)}}value={cardtype}style={{width:"content"}}/>
          </Form.Item>
          <Form.Item label={<label className='editCardLabel' name={"pokedesc"}>Pokemon Description</label>}>
            <Input onChange ={(e)=>{setCarddesc(e.target.value)}}value={carddesc}style={{width:"max-content"}}/>
          </Form.Item>
         

        </Form>
        </Space>
    </Modal>
        </>
  )
}

