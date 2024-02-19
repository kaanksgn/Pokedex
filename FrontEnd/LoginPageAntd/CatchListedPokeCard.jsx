import { Button, Card, Divider, Form, Input, Modal, Space } from 'antd'
import React, { useState } from 'react'
import {
  DeleteOutlined,EditOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';

export default function PokeCard({name,type,desc,id}){
  const [pokename, setPokename] = useState(name)
  const [poketype, setPoketype] = useState(type)
  const [pokedesc, setPokedesc] = useState(desc)

const addWishlist =()=>{
    axios.post(`/userdashboard/poke_add_to_lists?pokeId=${id}&which=wish`,{}).then(
        res=>{console.log(res.data)}
    )
}
  const [isModalOpen, setIsModalOpen] = useState(false);
  const showModal = () => {
    console.log(id)
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

 

const removeCatchlist =()=>{
  axios.post(`/userdashboard/remove_from_catchlist?pokeId=${id}`,{}).then(
      res=>{console.log(res.data)}
  )
}

  return (
    <><Card title={name} style={{color:"white"}} headStyle={{color:"black"}} className='userwishCard'  actions={
        [
        
            <Button className='userPokeCard' onClick={removeCatchlist}>Remove From Catchlist</Button>,
           
        ]
        }>
            <p > Type : {type}</p>
            <p>Description : {desc}</p>
        </Card>
       
      </>  
  )
}
