import React, { useEffect, useState } from 'react'
import { Button, Divider, Form, Input, List } from 'antd'
import PokeCard from './PokeCard'
import axios from '../../api/axios'
import AddCard from './AddCardPoke'
import AdminAddPoke from './AdminAddPoke'

export default function PokeOperations() {
  const [poke, setPoke] = useState([])

  
  const getPoke = (name,type) => {
    
    axios.get(`/admindashboard/poke_listby?`,{}
    ).then(res => {
      setPoke(res.data)
      console.log(res.data)
    })
  }

  useEffect(()=>{
      getPoke()
  },[])
  
  const [pokename, setPokename] = useState("")
  const [poketype, setPoketype] = useState("")
  const click=()=>{
    console.log(getPoke(pokename,poketype))
    var string = `/admindashboard/poke_listby?`

    string=pokename.length>0?string+`name=${pokename}&`:string;
    string= poketype.length>0?string+`type=${poketype}&`:string;
    axios.get(string,{}
    ).then(res => {
      setPoke(res.data)
      console.log(res.data)
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
          gutter: 13,
          column: 4,
        }}
        dataSource={poke}
        renderItem={(item) => (
          <List.Item style={{marginLeft:"55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
          <PokeCard className="itemCard" id={item.id} name={item.name} type={item.type} desc={item.description} key={item.id}/>
          </List.Item>
      
        )}
    
      >
        <List.Item style={{marginLeft:"55px",textAlign:"center",alignItems:"center",marginTop:"25px"}}>
          <div onClick={()=>{
          console.log("Clicked");
          }}>
            <AddCard className="itemCard"  />
          </div>
        </List.Item>
      </List>
    </>
  )
}
