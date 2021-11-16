import axios from "axios";
import Pagination from "components/Pagination";
import { useEffect, useState } from "react";
import { BreakfastPage } from "types/breakfast";
import { formatLocalDate } from "utils/format";
import { BASE_URL } from "utils/requests";

const DataTable = () => {
    const[activePage, setActivePage] = useState(0);
    const [page, setPage] = useState<BreakfastPage>({
        first: true,
        last: true,
        number: 0,
        totalElements: 0,
        totalPages: 0
    });

    useEffect(() => {
        axios.get(`${BASE_URL}/breakfastPage?page=${activePage}&size=20&sort=date,desc`)
            .then(response => {
                setPage(response.data);
            })
    }, [activePage]);

    const changePage = (index: number) => {
        setActivePage(index);
    }

    return (
        <>
        <Pagination page={page}onPageChange={changePage} />
            <div className="table-responsive">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Colaborador</th>
                            <th>Opção escolhida</th>                                                  
                        </tr>
                    </thead>
                    <tbody>
                        {page.content?.map(item => (
                            <tr key={item.id}>
                                <td>{formatLocalDate(item.date, "dd/MM/yyyy")}</td>
                                <td>{item.user.name}</td>                                
                                <td>{item.options}</td>                               
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    );

}

export default DataTable;