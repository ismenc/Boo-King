https://creately.com/app/?tempID=h165rwt81&login_type=demo#


<<ObjetoBookingGenerico>>
Arrendador
--
idArrendador : int
Nombre : String
Entidad : String
Dirección : String
Codigo Postal: int



<<ObjetoBookingGenerico>>
Prestamo
--
idPrestamo : int
Días duracion : int
Fecha : Date
Arrendador : Arrendador
ListaStacks : List<Stacks>



<<ObjetoBookingGenerico>>
Stack
--
idStack : int
Cantidad : int
Libro : Libro
Prestamo : Prestamo



<<ObjetoBookingGenerico>>
Libro
--
idLibro : int
Titulo : String
Autor : String
Categoría : Categoria
Editorial : String
Año publicación : String


<<Enumeration>>
Categoria
--
- Novela
- Cómic
- Poesía
- Biografía
- Psicología