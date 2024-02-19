import { Card, Col, Divider, List, Row } from 'antd'
import React, { useEffect, useState } from 'react'
import axios from '../../api/axios'
import WishlistedPokeCard from './WishlistedPokeCard'
import CatchListedPokeCard from './CatchListedPokeCard'
export default function UserDashboard() {

  const [wishlist, setWishlist] = useState([])
  const [catchlist, setCatchlist] = useState([])

  const getWishList=()=>{
    axios.get('/userdashboard/wishlisted_poke',{}).then(
      res=>{
       setWishlist(res.data)
        console.log(res)
      }
    )}

    const getCatchList=()=>{
      axios.get('/userdashboard/catchlisted_poke',{}).then(
        res=>{
         setCatchlist(res.data)
          console.log(res)
        }
      )}

  useEffect(()=>{
    getWishList(),
    getCatchList()
  },[])

  
  
  return (
    <>
    <div style={{flexDirection:"column"}}>
      <Row gutter={16}>
        <Col span={12}>
          
         
          <Row>
    <List header="Wishlist" className="wishlist"itemLayout='vertical'
        grid={{
          gutter: 0,
          column: 2,
        }}
        dataSource={wishlist}
        renderItem={(item) => (
          <List.Item style={{marginLeft:"-55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
          <WishlistedPokeCard className="itemCard" id={item.id} name={item.name} type={item.type} desc={item.description} key={item.id}/>
          </List.Item>
      
        )}
    
      /></Row>
      </Col>
      <Col span={12}>
      <List header="Catchlist" className="wishlist"itemLayout='vertical'
        grid={{
          gutter: 0,
          column: 2,
        }}
        dataSource={catchlist}
        renderItem={(item) => (
          <List.Item style={{marginLeft:"-55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
          <CatchListedPokeCard className="itemCard" id={item.id} name={item.name} type={item.type} desc={item.description} key={item.id}/>
          </List.Item>
      
        )}
    
      />
      </Col>
      </Row>
        </div>

    </>
  )
}
