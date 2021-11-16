import Footer from "components/Footer";
import NavBar from "components/NavBar";
import Access from "pages/Access";
import { Link } from "react-router-dom";

const Home = () => {
    return (
        <>
            <NavBar />
            <div className="container">
                <div className="jumbotron">
                    <h1 className="display-4">WL-Breakfast</h1>
                    <p className="lead">Organiza e agenda os eventos para café da manhã no Grupo WL</p>
                    <hr />
                    <p>Esta aplicação consiste em organizar os insumos e produtos trazidos pelos colaboradores do Grupo WL.</p>
                    <Link className="btn btn-primary btn-lg" to="/login">
                       <Access />
                    </Link>
                </div>
            </div>
            <Footer />
        </>
    );
}

export default Home;