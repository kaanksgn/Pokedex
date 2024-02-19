import { Button, Card, Divider, Form, Input, Modal, Space } from 'antd'
import React, { useEffect, useState } from 'react'
import {
  DeleteOutlined,EditOutlined
 } from '@ant-design/icons';
import axios from '../../api/axios';
import AddWishlistButton from './AddWishlistButton';
import AddCatchlistButton from './AddCatchlistButton';

export default function PokeCard({name,type,desc,id}){
  const [pokename, setPokename] = useState(name)
  const [poketype, setPoketype] = useState(type)
  const [pokedesc, setPokedesc] = useState(desc)


  const [wishlist, setWishlist] = useState([])
  const [catchlist, setCatchlist] = useState([])

  const getWishList=()=>{
    axios.get('/userdashboard/wishlisted_poke',{}).then(
      res=>{
       setWishlist(res.data)
        console.log("wishlist : "+res)
      }
    )}

    useEffect(()=>{
        getWishList(),
        getCatchList()
      },[])

    const getCatchList=()=>{
      axios.get('/userdashboard/catchlisted_poke',{}).then(
        res=>{
         setCatchlist(res.data)
          
        }
      )}


  


  var iswishlisted = false;

  for(let i=0;i<wishlist.length;i++){
    if(wishlist[i].id==id){
        iswishlisted=true;
    }
    

    var iscatchlisted = false;
  
    for(let i=0;i<catchlist.length;i++){
      if(catchlist[i].id==id){
        iscatchlisted=true;
      }}
  

    
  }
  return (
    <><Card title={name} style={{color:"white"}} headStyle={{color:"black"}} className='userCard'  actions={
        [
          
            <AddWishlistButton param={iswishlisted} id={id}/>,
            <AddCatchlistButton param={iscatchlisted} id={id}/>
        ]
        }>
            <p > Type : {type}</p>
            <p>Description : {desc}</p>
        </Card>
       
      </>  
  )
}
