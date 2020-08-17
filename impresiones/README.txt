Este es un software que busca los archivos con extención .pdf de determinada carpeta y los enlista para que, posteriomente, el usuario seleccione los archivos que desea imprimir y se genere un informe de excel de los archivos que fueron impresos.
Este software fue hecho con el fin de cumplir con una de las especificaciones para el sistema de gestión de calidad de una empresa

Este programa crea una carpeta oculta lamada files (.files) con dos archivos en ella (uno para almacenar las rutas y otro para almacenar la contraseña encriptada bajo md5).

Se debe crear el archivo .jar para la ejecución del programa y se recomienda no mover el ejecutable a otra ruta.
En caso de que sea estrictamente necesario cambiar la ruta del .jar, se deberá también cambiar la ruta de la caprteta files.

El administrador del sistema de control de impresiones debe definir una contraseña, la cual servirá para cambiar las rutas de los archivos imprimibles y del reporte de impresiones.
Se recomienda que no se modifique el archivo de rutas manualmente, pues podrían ocurrir errores en la ejecución del programa.

Para hacer cambios en las rutas o en la contraseña, se debe presionar la letra c en la ventana de impresión.

NOTA: Este proyecto fue realizado sobre Debian GNU/Linux 10 (buster). En caso de que su funcionamiento en sistemas Windows no sea óptimo, se deberán realizar las modificaciones necesarias.
