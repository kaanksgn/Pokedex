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

  const removeWishlist =()=>{
    axios.post(`/userdashboard/remove_from_wishlist?pokeId=${id}`,{}).then(
        res=>{console.log(res.data)}
    )
}

const removeCatchlist =()=>{
  axios.post(`/userdashboard/remove_from_catchlist?pokeId=${id}`,{}).then(
      res=>{console.log(res.data)}
  )
}

  return (
    <><Card title={name} style={{color:"white"}} headStyle={{color:"black"}} className='userwishCard'  actions={
        [
        
            <Button className='userPokeCard' onClick={removeWishlist}>Remove From WishList</Button>,
           
        ]
        }>
            <p > Type : {type}</p>
            <p>Description : {desc}</p>
        </Card>
       
      </>  
  )
}
