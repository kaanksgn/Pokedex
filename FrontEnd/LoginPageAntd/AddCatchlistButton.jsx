import React, { useEffect, useState } from 'react'
import axios from '../../api/axios'
import { Button } from 'antd'

export default function AddWishlistButton({param,id}) {

    const [wishlist, setWishlist] = useState([])
  const [catchlist, setCatchlist] = useState([])
const addCatchlist =()=>{
    axios.post(`/userdashboard/poke_add_to_lists?pokeId=${id}&which=catch`,{}).then(
        res=>{console.log(res.data)}
    )
}

const removeCatchlist =()=>{
    axios.post(`/userdashboard/remove_from_catchlist?pokeId=${id}`,{}).then(
        res=>{console.log(res.data)}
    )
}
  
    if(param){
        return (
            <Button className='wishlistedcard' onClick={removeCatchlist} >Catchlisted</Button>
          ) 
    }else { return (
        <Button className='userPokeCard' onClick={addCatchlist} >Add To Catchlist</Button>
      )}
 
}

