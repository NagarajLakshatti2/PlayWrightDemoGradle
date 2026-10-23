# AI Learning Project - Complete End-to-End Implementation

A comprehensive practical implementation of AI concepts from basics to advanced multi-agent systems.

## 📚 Learning Topics Covered

### 1. **AI/ML/DL Basics**
- Traditional ML algorithms
- Deep Learning fundamentals
- Neural network architectures

### 2. **Large Language Models (LLM)**
- Hugging Face Transformers
- Text generation and understanding
- Model fine-tuning concepts

### 3. **Retrieval-Augmented Generation (RAG)**
- Vector databases (ChromaDB)
- Embedding models
- Context retrieval
- Knowledge grounding

### 4. **Single AI Agent**
- Tool execution
- Agent loops
- Decision making
- LangChain agents

### 5. **Multi-Agent Systems**
- Agent orchestration
- Specialized agents
- Inter-agent communication
- LangGraph workflows

### 6. **Memory Management**
- Short-term memory
- Long-term memory
- Vector stores
- Context management

### 7. **Reasoning Strategies**
- Chain of Thought (CoT)
- Tree of Thoughts (ToT)
- Self-reflection
- ReAct pattern

### 8. **Orchestration**
- Task planning
- Resource allocation
- Routing and dispatch
- Result synthesis

## 🏗️ Project Structure

```
ai-learning-project/
├── docs/                    # Learning documents for each topic
│   ├── 01_basics.md
│   ├── 02_llm.md
│   ├── 03_rag.md
│   ├── 04_single_agent.md
│   ├── 05_multi_agent.md
│   ├── 06_memory.md
│   ├── 07_reasoning.md
│   └── 08_orchestration.md
├── modules/                 # Implementation modules
│   ├── basics/
│   ├── llm/
│   ├── rag/
│   ├── single_agent/
│   ├── multi_agent/
│   ├── memory/
│   ├── reasoning/
│   └── orchestration/
├── examples/                # Practical examples
│   ├── basic_examples.py
│   ├── llm_examples.py
│   ├── rag_examples.py
│   ├── agent_examples.py
│   └── full_pipeline.py
├── tests/                   # Unit tests
└── main.py                  # Main integration demo
```

## 🚀 Setup Instructions

### Prerequisites
- Python 3.9 or higher
- pip package manager

### Installation

```bash
# Create virtual environment
python -m venv venv

# Activate virtual environment
# On Windows:
venv\Scripts\activate
# On Mac/Linux:
source venv/bin/activate

# Install dependencies
pip install -r requirements.txt
```

### Dependencies

```txt
torch
transformers
langchain
langchain-community
langchain-openai
chromadb
sentence-transformers
faiss-cpu
numpy
pandas
scikit-learn
openai
python-dotenv
tiktoken
```

### Environment Variables

Create a `.env` file in the project root:

```env
# Optional: OpenAI API Key (for some examples)
OPENAI_API_KEY=your_key_here

# Hugging Face API (free, no key required for basic usage)
HUGGINGFACE_API_KEY=your_key_here

# Optional: For better models
```

## 📖 Learning Path

### Step 1: Start with Basics
- Read: `docs/01_basics.md`
- Run: `examples/basic_examples.py`

### Step 2: Learn LLMs
- Read: `docs/02_llm.md`
- Run: `examples/llm_examples.py`

### Step 3: Build RAG System
- Read: `docs/03_rag.md`
- Run: `examples/rag_examples.py`

### Step 4: Create Single Agent
- Read: `docs/04_single_agent.md`
- Run: `examples/agent_examples.py`

### Step 5: Build Multi-Agent System
- Read: `docs/05_multi_agent.md`
- Run: `examples/full_pipeline.py`

### Step 6: Advanced Topics
- Memory: `docs/06_memory.md`
- Reasoning: `docs/07_reasoning.md`
- Orchestration: `docs/08_orchestration.md`

## 🎯 Running the Full Demo

```bash
python main.py
```

This will run a complete end-to-end demonstration of all components working together.

## 📝 Module Details

Each module contains:
- **Learning Document**: Theory and concepts
- **Implementation Code**: Practical Python code
- **Examples**: Runnable examples
- **Tests**: Unit tests

## 🛠️ Technologies Used

All technologies are free and open-source:

- **Hugging Face Transformers**: Pre-trained models
- **ChromaDB**: Vector database for RAG
- **LangChain**: Agent framework
- **LangGraph**: Multi-agent orchestration
- **Sentence Transformers**: Embedding models
- **PyTorch**: Deep learning framework

## 📖 Free APIs Guide

**NEW**: Check out [FREE_APIS_GUIDE.md](FREE_APIS_GUIDE.md) for detailed information about all the free APIs and libraries used in this project, including:

- How each API works
- Practical code examples
- Installation instructions
- Pricing information (all free!)
- Where they're used in the project

All APIs used in this project are **100% free** and don't require API keys for basic usage!

## 🤝 Contributing

This is a learning project. Feel free to:
- Add new examples
- Improve documentation
- Fix bugs
- Add new features

## 📄 License

MIT License - Free for educational and commercial use

## 🎓 Learning Resources

- [Hugging Face Documentation](https://huggingface.co/docs)
- [LangChain Documentation](https://python.langchain.com/)
- [ChromaDB Documentation](https://docs.trychroma.com/)
- [LangGraph Documentation](https://langchain-ai.github.io/langgraph/)

---

**Happy Learning! 🚀**
