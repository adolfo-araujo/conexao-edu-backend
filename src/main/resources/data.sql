INSERT INTO cursos (nome, codigo, descricao, carga_horaria)
SELECT 'Matemática', 'MAT101', 'Fundamentos e exercícios de matemática.', 80
WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'MAT101');

INSERT INTO cursos (nome, codigo, descricao, carga_horaria)
SELECT 'Ciências', 'CIE102', 'Conceitos básicos de ciências naturais.', 60
WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'CIE102');

INSERT INTO cursos (nome, codigo, descricao, carga_horaria)
SELECT 'Geografia', 'GEO103', 'Conteúdos introdutórios de geografia.', 50
WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'GEO103');

INSERT INTO cursos (nome, codigo, descricao, carga_horaria)
SELECT 'Português', 'POR104', 'Leitura, interpretação e produção textual.', 70
WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'POR104');

INSERT INTO post (conteudo)
SELECT 'Lima: Análise espacial é loucura.'
WHERE NOT EXISTS (SELECT 1 FROM post WHERE conteudo = 'Lima: Análise espacial é loucura.');

INSERT INTO post (conteudo)
SELECT 'Guilherme: Eu gostei bastante!'
WHERE NOT EXISTS (SELECT 1 FROM post WHERE conteudo = 'Guilherme: Eu gostei bastante!');

INSERT INTO post (conteudo)
SELECT 'Caio: Vou cursar direito, desisto da engenharia.'
WHERE NOT EXISTS (SELECT 1 FROM post WHERE conteudo = 'Caio: Vou cursar direito, desisto da engenharia.');
