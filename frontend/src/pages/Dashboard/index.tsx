import NavBar from "components/NavBar";
import DataTable from "components/DataTable";
import Footer from "components/Footer";



const Dashboard = () => {
    return (
        <>
            <NavBar />

            <div className="container">
                <h1 className="text-primary py-3">Dashboard de eventos</h1>

                <div className="row px-3">
                    <div className="col-sm-6">
                        <h5>Todos os eventos</h5>
                    </div>
                </div>
                <DataTable />
            </div>
            <Footer />
        </>
    );
}

export default Dashboard;