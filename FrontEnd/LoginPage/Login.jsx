import { useRef,useState,useEffect, useContext } from "react";
import AuthContext from "./Context/Auth";
import axios from '../../api/axios'
const Login =()=>{

    const {setAuth} = useContext(AuthContext);
    const userRef = useRef();
    const errRef = useRef();

    const [user,setUser] = useState('');
    const [pwd,setPwd] = useState('');
    const [errMsg,setErrMsg] = useState('');
    const [success,setSuccess] = useState(false);

    useEffect(()=>{
        userRef.current.focus();
    },[])

    useEffect(()=>{
        setErrMsg('');
    },[user,pwd])

    const handleSubmit = async(e)=>{
        e.preventDefault();

        console.log(user,pwd);

        try{
            const response = await axios.post(
                "http://localhost:8080/login",
                JSON.stringify({user,pwd}),
                {
                    headers: {'Content-Type': 'application/json'},
                    withCredentials: true
                }
            );
            console.log(JSON.stringify(response?.data))
            const accessToken = response?.data?.accessToken;
            const roles = response?.data?.roles;
            setAuth({user,pwd,roles,accessToken })
            setUser('');
            setPwd('');
        }catch(err){
            if(!err?.response){
                setErrMsg('No Server Response')
            }else if(err.response?.status === 400){
                setErrMsg('Missing Username or Password')
            }else if(err.response?.status === 401){
                setErrMsg('Unauthorized')
            }else{
                setErrMsg('Login Failed')
            }
            err.current.focus()
        }
        setSuccess(true);
    }
    return (
        <>
            {success ? 
            (<section>
                <h1>Logged In</h1>
                <br/>
                <p>
                    <a href="#">Go Home</a>
                </p>
            </section>):
            (
            <section>
                <h1>Sign In</h1>
                
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username:</label>
                <br/>
                <input type="text"
                        id="username" 
                        placeholder="Username" 
                        ref={userRef} 
                        autoComplete="off"
                        onChange={(e)=> setUser(e.target.value)}
                        value={user}
                        required
                        /><br/>
                <label htmlFor="password">Password:</label>
                <br/>
                <input type="password"
                        id="password" 
                        placeholder="Password"  
                        onChange={(e)=> setPwd(e.target.value)}
                        value={pwd}
                        required
                        />
                        <br/>
                        <button>Sign In</button>
            </form>
            </section>)}
        
      
        </>
    )
}
export default Login;