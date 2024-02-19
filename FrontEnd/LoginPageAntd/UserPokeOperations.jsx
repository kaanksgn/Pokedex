import React, { useEffect, useState } from 'react'
import { Button, Divider, Form, Input, List } from 'antd'
import UserPokeCard from './UserPokeCard'
import axios from '../../api/axios'
import AddCard from './AddCardPoke'
import AdminAddPoke from './AdminAddPoke'

export default function UserPokeOperations() {
  const [poke, setPoke] = useState([])
  const getPoke = (name,type) => {
    
    axios.get(`/userdashboard/poke_listby?`,{}
    ).then(res => {
      setPoke(res.data)
    })
  }

  useEffect(()=>{
      getPoke()
     
    
  },[])

    
  
  const [pokename, setPokename] = useState("")
  const [poketype, setPoketype] = useState("")
  const click=()=>{
    
    var string = `/userdashboard/poke_listby?`
    string=pokename.length>0?string+`name=${pokename}&`:string;
    string= poketype.length>0?string+`type=${poketype}&`:string;
    axios.get(string,{}
    ).then(res => {
      setPoke(res.data)
      
    })

    setPokename("")
    setPoketype("")
  }   


 
  return (
    <>
      <Divider/>

      <div>
        <div className='inputDiv'>
          <h3 style={{padding:"10px"}}>Pokemon Name</h3>
          <input  className='userOpInput' type='text' value={pokename} onChange={(e)=>{setPokename(e.target.value)}} ></input>
          <h3 style={{padding:"10px" ,marginLeft:"55px"} }>Pokemon Type</h3>
          
          <input className='userOpInput' type='text' value={poketype} onChange={(e)=>{setPoketype(e.target.value)}} ></input>
         
          <Button className='userOpButton' onClick={click}>List Pokemons</Button>
        </div>
        <Divider/>
      </div>
      <List
        grid={{
          gutter: 0,
          column: 4,
        }}
        dataSource={poke}
        renderItem={(item) => (
          <List.Item style={{marginLeft:"55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
          <UserPokeCard className="itemCard" id={item.id} name={item.name} type={item.type} desc={item.description} key={item.id}/>
          </List.Item>
      
        )}
    
      >
       
      </List>
    </>
  )
}
