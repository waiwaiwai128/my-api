// import { PageContainer } from '@ant-design/pro-components';
// import React, { useEffect, useState } from 'react';
// import { List, message } from 'antd';
// import { listInterfaceInfoByPageUsingGET } from '@/services/yuapi-backend/interfaceInfoController';
//
// /**
//  * 主页
//  * @constructor
//  */
// const Index: React.FC = () => {
//   const [loading, setLoading] = useState(false);
//   const [list, setList] = useState<API.InterfaceInfo[]>([]);
//   const [total, setTotal] = useState<number>(0);
//
//   const loadData = async (current = 1, pageSize = 5) => {
//     setLoading(true);
//     try {
//       const res = await listInterfaceInfoByPageUsingGET({
//         current,
//         pageSize,
//       });
//       setList(res?.data?.records ?? []);
//       setTotal(res?.data?.total ?? 0);
//     } catch (error: any) {
//       message.error('请求失败，' + error.message);
//     }
//     setLoading(false);
//   };
//
//   useEffect(() => {
//     loadData();
//   }, []);
//
//   return (
//     <PageContainer title="在线接口开放平台">
//       <List
//         className="my-list"
//         loading={loading}
//         itemLayout="horizontal"
//         dataSource={list}
//         renderItem={(item) => {
//           const apiLink = `/interface_info/${item.id}`;
//           return (
//             <List.Item actions={[<a key={item.id} href={apiLink}>查看</a>]}>
//               <List.Item.Meta
//                 title={<a href={apiLink}>{item.name}</a>}
//                 description={item.description}
//               />
//             </List.Item>
//           );
//         }}
//         pagination={{
//           // eslint-disable-next-line @typescript-eslint/no-shadow
//           showTotal(total: number) {
//             return '总数：' + total;
//           },
//           pageSize: 5,
//           total,
//           onChange(page, pageSize) {
//             loadData(page, pageSize);
//           },
//         }}
//       />
//     </PageContainer>
//   );
// };
//
// export default Index;


import { PageContainer } from '@ant-design/pro-components';
import React, { useEffect, useState } from 'react';
import { List, message, Button } from 'antd'; // 引入Button
import { listInterfaceInfoByPageUsingGET } from '@/services/yuapi-backend/interfaceInfoController';
import { addInvokeCountUsingPOST } from '@/services/yuapi-backend/userInterfaceInfoController'; // 引入API调用

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  const [total, setTotal] = useState<number>(0);

  const loadData = async (current = 1, pageSize = 5) => {
    setLoading(true);
    try {
      const res = await listInterfaceInfoByPageUsingGET({
        current,
        pageSize,
      });
      setList(res?.data?.records ?? []);
      setTotal(res?.data?.total ?? 0);
    } catch (error: any) {
      message.error('请求失败，' + error.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleAddInvokeCount = async (userId: number, interfaceInfoId: number) => {
    try {
      const res = await addInvokeCountUsingPOST({ userId, interfaceInfoId });
      if (res) {
        message.success('调用次数增加成功');
      }
    } catch (error: any) {
      message.error('增加调用次数失败，' + error.message);
    }
  };

  return (
    <PageContainer title="在线接口开放平台">
      <List
        className="my-list"
        loading={loading}
        itemLayout="horizontal"
        dataSource={list}
        renderItem={(item) => {
          const apiLink = `/interface_info/${item.id}`;
          return (
            <List.Item
              actions={[
                <Button
                  key={`add-invoke-${item.id}`}
                  type="link"
                  onClick={() => handleAddInvokeCount(1, item.id)} // 这里的1是示例userId
                >
                  增加调用次数
                </Button>,
                <a key={item.id} href={apiLink}>查看</a>
              ]}
            >
              <List.Item.Meta
                title={<a href={apiLink}>{item.name}</a>}
                description={item.description}
              />
            </List.Item>
          );
        }}
        pagination={{
          showTotal(total: number) {
            return '总数：' + total;
          },
          pageSize: 5,
          total,
          onChange(page, pageSize) {
            loadData(page, pageSize);
          },
        }}
      />
    </PageContainer>
  );
};

export default Index;

