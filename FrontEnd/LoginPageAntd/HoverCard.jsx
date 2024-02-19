import React from 'react'

export default function HoverCard() {
    return (
        <><Card 
         style={{color:"white"}}  className='userCard'
          actions={
            [
            
    
            ]
            }>
                <p > Type : {type}</p>
                <p>Description : {desc}</p>
            </Card>
            
          </>  
      )
}
