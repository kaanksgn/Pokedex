import React, { useEffect, useState } from 'react'
import axios from '../../api/axios'
import { Button } from 'antd'

export default function AddWishlistButton({param,id}) {

    const [wishlist, setWishlist] = useState([])
  const [catchlist, setCatchlist] = useState([])
  const addWishlist =()=>{
    axios.post(`/userdashboard/poke_add_to_lists?pokeId=${id}&which=wish`,{}).then(
        res=>{console.log(res.data)}
    )
}

const removeWishlist =()=>{
    axios.post(`/userdashboard/remove_from_wishlist?pokeId=${id}`,{}).then(
        res=>{console.log(res.data)}
    )
}

  
    if(param){
        return (
            <Button className='wishlistedcard'onClick={removeWishlist} >Wishlisted</Button>
          ) 
    }else { return (
        <Button className='userPokeCard' onClick={addWishlist} >Add To WishList</Button>
      )}
 
}
